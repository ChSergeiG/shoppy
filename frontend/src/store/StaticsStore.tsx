import {createEvent, createStore} from "effector";
import type {IStatus} from "../../types/IStatus";
import type {IAccountRole} from "../../types/IAccountRole";
import {getAccountRoles, getStatuses} from "../utils/API";

export type IStaticsStore = {
    cacheDate?: number;
    statuses: IStatus[];
    accountRoles: IAccountRole[];
};

const STATICS_LOCAL_STORAGE_KEY = "STATICS_LOCAL_STORAGE_KEY";

const currentState = localStorage.getItem(STATICS_LOCAL_STORAGE_KEY);

export const staticsStore = createStore<IStaticsStore>({
    statuses: [],
    accountRoles: [],
});

export const refreshStatics = createEvent<void>("Refresh all statics");

export const refreshStatuses = createEvent<IStatus[]>("Refresh actual statuses");

export const refreshAccountRoles = createEvent<IAccountRole[]>("Refresh actual account roles");

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
        getStatuses()
            .then((r) => refreshStatuses(r.data));
        getAccountRoles()
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
