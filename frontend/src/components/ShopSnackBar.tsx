import {Alert, Snackbar} from "@mui/material";
import React, {useContext, useEffect, useState} from "react";
import {ApplicationContext} from "../applicationContext";

type ISnackbarState = {
    isOpened: boolean;
};

export const ShopSnackBar: React.FC = () => {

    const [state, setState] = useState<ISnackbarState>();
    const context = useContext(ApplicationContext);

    useEffect(() => {
        if (context.message) {
            setState({...state, isOpened: true})
        }
    }, [context.message]);
    return (
        <Snackbar
            anchorOrigin={{vertical: 'bottom', horizontal: 'left'}}
            open={state?.isOpened}
            onClose={() => setState?.({...state, isOpened: false})}
        >
            <Alert
                onClose={() => setState?.({...state, isOpened: false})}
                severity={context.color}
            >
                {context.message.slice(39)}
            </Alert>
        </Snackbar>
    );
};

