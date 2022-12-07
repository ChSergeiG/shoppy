/**
 * Store for user authorization
 */
import {createEvent, createStore} from "effector";
import {logout as doLogout, probeToken} from "../utils/API";
import {Cookies, useCookies} from "react-cookie";
import type {Cookie} from "universal-cookie/cjs/types";

const COOKIE_JWT_KEY = "COOKIE_JWT_KEY";

const LOCAL_STORAGE_JWT_KEY = "LOCAL_STORAGE_JWT_KEY";

const authCookies = new Cookies();

export type IAuthorizationStore = {
    token?: string | null;
    authorized: boolean;
}

export const authorizationStore = createStore<IAuthorizationStore>({
    token: localStorage.getItem(LOCAL_STORAGE_JWT_KEY),
    authorized: false,
});

export const updateAuthorizationState = createEvent<boolean>("Set authorization state");

export const updateAuthorizationToken = createEvent<string>("Set token value");

export const verifyAuthorization = createEvent<void>("Check authorization");

export const logout = createEvent<void>("Do logout");

authorizationStore
    .on(updateAuthorizationState, (state, newState) => {
        authCookies.set(COOKIE_JWT_KEY, newState ? state.token : '')
        return {...state, authorized: newState};
    })
    .on(updateAuthorizationToken, (state, newToken) => {
        return {...state, token: newToken};
    })
    .on(verifyAuthorization, (state, payload) => {
        if (state.token) {
            probeToken(state.token)
                .then((r) => {
                    if (state.token && r.data) {
                        localStorage.setItem(LOCAL_STORAGE_JWT_KEY, state.token);
                    }
                    updateAuthorizationState(r.data);
                })
                .catch((_) => updateAuthorizationState(false));
        } else {
            updateAuthorizationState(false);
        }
    })
    .on(logout, (state) => {
        localStorage.setItem(LOCAL_STORAGE_JWT_KEY, "");
        doLogout().then((_) => {
        });
        return {...state, authorized: false, token: ""};
    })
    .watch(async (state) => {
        if (state.token) {
            probeToken(state.token)
                .then((r) => r.data && state.token && localStorage.setItem(LOCAL_STORAGE_JWT_KEY, state.token));
        }
    });

//setInterval(() => verifyAuthorization(), 5_000);
