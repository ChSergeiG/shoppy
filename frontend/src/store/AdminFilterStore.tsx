import {createEvent, createStore} from "effector";

export type IFilterEntry = { key: string, value: string };

export const adminFilterStore = createStore<IFilterEntry[]>([]);

export const setAdminFilter = createEvent<IFilterEntry>("Set admin filter value");

adminFilterStore
    .on(setAdminFilter, (state, entry) => {
        const newState = [];
        const idx = state.map(e => e.key).indexOf(entry.key);
        for (let i = 0; i < state.length; i++) {
            if (i !== idx) {
                newState.push(state[i]);
            } else {
                newState.push(entry);
            }
        }
        if (idx === -1) {
            newState.push(entry);
        }
        console.log(newState)
        return newState;
    });

