import React from "react";
import {useStore} from "effector-react";
import {alertStore} from "../store/AlertStore";
import {Dialog} from "@mui/material";

const AlertDialog: React.FC = () => {

    const shoppyAlertStore = useStore(alertStore);

    return (
        <Dialog
            open={shoppyAlertStore.show}
        >
            {shoppyAlertStore.children}
        </Dialog>
    );
};

export default AlertDialog;
