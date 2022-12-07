/**
 * Store for storable data like statuses or roles enum etc.
 * These items obtained via API and stores locally until refresh invocation
 */
import {createEvent, createStore} from "effector";
import type {AccountRole, Status} from "../types";
import {getAllRoles, getAllStatuses} from "../utils/API";

export type IStaticsStore = {
    cacheDate?: number;
    statuses: Status[];
    accountRoles: AccountRole[];
};

const STATICS_LOCAL_STORAGE_KEY = "STATICS_LOCAL_STORAGE_KEY";

const currentState = localStorage.getItem(STATICS_LOCAL_STORAGE_KEY);

export const staticsStore = createStore<IStaticsStore>({
    statuses: [],
    accountRoles: [],
});

export const refreshStatics = createEvent<void>("Refresh all statics");

export const refreshStatuses = createEvent<Status[]>("Refresh actual statuses");

export const refreshAccountRoles = createEvent<AccountRole[]>("Refresh actual account roles");

export const setCachedDate = createEvent<number>("Internal method to set last refresh date");

staticsStore
    .on(refreshStatuses, (state, newStatuses) => {
        return {...state, statuses: newStatuses};
    })
    .on(refreshAccountRoles, (state, newAccountRoles) => {
        return {...state, accountRoles: newAccountRoles};
    })
    .on(setCachedDate, (state, newDate) => {
        return {...state, cacheDate: newDate};
    })
    .on(refreshStatics, (state) => {
        getAllStatuses()
            .then((r) => refreshStatuses(r.data));
        getAllRoles()
            .then((r) => refreshAccountRoles(r.data))
        setCachedDate(Date.now());
        localStorage.setItem(STATICS_LOCAL_STORAGE_KEY, JSON.stringify(staticsStore.getState()));
    });

if (
    currentState !== null
    && currentState.length > 0
    && JSON.parse(currentState).cacheDate > Date.now() + 1000 * 60 * 60
) {
    refreshStatics();
}
