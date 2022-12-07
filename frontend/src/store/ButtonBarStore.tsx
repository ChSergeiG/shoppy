/**
 * Store for ButtonBar in main menu {@link ButtonBar}
 */
import {createEvent, createStore} from "effector";
import type {IButtonBarItem} from "../components/ButtonBar";

export const buttonBarStore = createStore<IButtonBarItem[]>([]);

export const setButtons = createEvent<IButtonBarItem[]>("Set new buttons");

buttonBarStore
    .on(setButtons, (state, newState) => newState);