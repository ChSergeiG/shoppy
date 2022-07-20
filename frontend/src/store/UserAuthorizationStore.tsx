import {createEvent, createStore} from "effector";
import {getLogout, getProbeLogin} from "../utils/API";

const LOCAL_STORAGE_JWT_KEY = "LOCAL_STORAGE_JWT_KEY";

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

export const verifyAuthorization = createEvent("Check authorization");

export const logout = createEvent<void>("Do logout");

authorizationStore
    .on(updateAuthorizationState, (state, newState) => {
        return {...state, authorized: newState};
    })
    .on(updateAuthorizationToken, (state, newToken) => {
        return {...state, token: newToken};
    })
    .on(verifyAuthorization, (state) => {
        if (state.token) {
            getProbeLogin(state.token)
                .then((r) => {
                    if (state.token && r.data) {
                        localStorage.setItem(LOCAL_STORAGE_JWT_KEY, state.token);
                    }
                    updateAuthorizationState(r.data);
                })
        } else {
            updateAuthorizationState(false);
        }
    })
    .on(logout, (state) => {
        localStorage.setItem(LOCAL_STORAGE_JWT_KEY, "");
        getLogout().then((_) => {
        });
        return {...state, authorized: false, token: ""};
    })
    .watch(async (state) => {
        if (state.token) {
            getProbeLogin(state.token)
                .then((r) => r.data && state.token && localStorage.setItem(LOCAL_STORAGE_JWT_KEY, state.token));
        }
    });

verifyAuthorization();
