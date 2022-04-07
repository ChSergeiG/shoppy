import {Alert, Snackbar} from "@mui/material";
import React, {useEffect, useState} from "react";
import {snackBarStore} from "../store/SnackBarStore";
import {useStore} from "effector-react";

type ISnackbarState = {
    isOpened: boolean;
};

export const ShopSnackBar: React.FC = () => {

    const [state, setState] = useState<ISnackbarState>();
    const store = useStore(snackBarStore);

    useEffect(() => {
        if (store.message) {
            setState({...state, isOpened: true})
        }
    }, [store.message]);
    return (
        <Snackbar
            anchorOrigin={{vertical: 'bottom', horizontal: 'left'}}
            open={state?.isOpened}
            onClose={() => setState?.({...state, isOpened: false})}
        >
            <Alert
                onClose={() => setState?.({...state, isOpened: false})}
                severity={store.color}
            >
                {store.message.slice(39)}
            </Alert>
        </Snackbar>
    );
};

