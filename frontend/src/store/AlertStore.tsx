/**
 * Store for overlay alert component {@link AlertDialog}
 */
import {createEvent, createStore} from "effector";

export type IAlertStore = {
    show: boolean;
    children?: JSX.Element;
}

export const alertStore = createStore<IAlertStore>({
    show: false,
});

export const showAlert = createEvent("Show overlay alert");

export const closeAlert = createEvent("Close overlay alert");

export const setAlertContent = createEvent<JSX.Element>("Set that, what we want to display");

alertStore
    .on(showAlert, (state) => {
        return {...state, show: true};
    })
    .on(closeAlert, (state) => {
        return {...state, show: false};
    })
    .on(setAlertContent, (state, content) => {
        return {...state, children: content};
    });