import {Alert, AlertColor, Snackbar} from "@mui/material";
import React, {useContext, useEffect, useState} from "react";
import {SnackBarContext} from "../snackBarContext";

type ISnackbarState = {
    isOpened: boolean;
};

export const SNACKBAR_MESSAGE_VALUE_KEY = "SNACKBAR_MESSAGE_VALUE_KEY";
export const SNACKBAR_MESSAGE_SEVERITY_KEY = "SNACKBAR_MESSAGE_VALUE_KEY";
export const SNACKBAR_EVENT_KEY = "SNACKBAR_EVENT_KEY";

export const ShopSnackBar: React.FC = () => {
    const [state, setState] = useState<ISnackbarState>();
    const context = useContext(SnackBarContext);

    useEffect(() => {
        if (context.message) {
            setState({isOpened: true})
        }
    }, [context.message]);

    return (
        <Snackbar
            anchorOrigin={{vertical: 'bottom', horizontal: 'left'}}
            open={state && state.isOpened}
            onClose={() => state && setState({...state, isOpened: false})}
        >
            <Alert
                onClose={() => state && setState({...state, isOpened: false})}
                severity={context.color}
            >
                {context.message}
            </Alert>
        </Snackbar>
    );
};

