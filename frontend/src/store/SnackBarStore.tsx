import {createEvent, createStore} from "effector";
import type {AlertColor} from "@mui/material";

export type ISnackBarEvent = {
    message: string;
    color: AlertColor;
};

export const snackBarStore = createStore<ISnackBarEvent>({
    message: "",
    color: "success",
});

export const placeSnackBarAlert = createEvent<ISnackBarEvent>("Register new snackbar event");

snackBarStore
    .on(placeSnackBarAlert, (state, event) => {
        return {...state, message: `[${Date.now()}] ${event.message}`, color: event.color};
    });
