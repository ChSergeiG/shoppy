import {createEvent, createStore} from "effector";
import type {IGood} from "../../types/AdminTypes";

const LOCAL_STORAGE_BASKET = "LOCAL_STORAGE_BASKET";

const storedValue = localStorage.getItem(LOCAL_STORAGE_BASKET);

export const selectedGoods = createStore<IGood[]>(JSON.parse(!!storedValue ? storedValue : "[]"));

export const addGoodToBasket = createEvent<IGood>("Add good into basket");

export const removeGoodFromBasket = createEvent<IGood>("Remove good from basket");

export const removeAllFromBasket = createEvent<void>("Remove all form basket");

selectedGoods
    .on(addGoodToBasket, (state, newGood) => [...state, newGood])
    .on(removeGoodFromBasket, (state, goodToRemove) => {
        const newState = [];
        const idx = state.indexOf(goodToRemove);
        for (let i = 0; i < state.length; i++) {
            if (i !== idx) {
                newState.push(state[i]);
            }
        }
        return newState;
    })
    .on(removeAllFromBasket, () => [])
    .watch((state) => localStorage.setItem(LOCAL_STORAGE_BASKET, JSON.stringify(state)));
