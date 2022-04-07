import {createEvent, createStore} from "effector";

export const adminFilterStore = createStore<string>("");

export const setAdminFilter = createEvent<string>("Set admin filter value");

adminFilterStore
    .on(setAdminFilter, (state, newFilterValue) => newFilterValue);

