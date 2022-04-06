import React from "react";
import type {AlertColor} from "@mui/material";
import type {IApplicationContext} from "../types/IApplicationContextType";
import type {IStatus} from "../types/IStatus";
import type {IAccountRole} from "../types/IAccountRole";
import type {IButtonBarItem} from "./components/ButtonBar";

export const ApplicationContext = React.createContext<IApplicationContext>({
    message: "",
    color: "success",
    statuses: [],
    accountRoles: [],
});

export const ApplicationContextProvider: React.FC<React.PropsWithChildren<{}>> = (props) => {

    const [adminFilter, setAdminFilter] = React.useState<string>("");

    const [message, setMessage] = React.useState<string>("");
    const [color, setColor] = React.useState<AlertColor>("success");

    const [statuses, setStatuses] = React.useState<IStatus[]>([]);
    const [accountRoles, setAccountRoles] = React.useState<IAccountRole[]>([]);

    const [buttonBarItems, setButtonBarItems] = React.useState<IButtonBarItem[]>([]);

    const setValues: IApplicationContext["setSnackBarValues"] = ({message, color}) => {
        setMessage(`[${crypto.randomUUID()}] ${message}`);
        setColor(color);
    };

    return (
        <ApplicationContext.Provider
            children={props.children}
            value={{
                adminFilter, setAdminFilter,
                message, color, setSnackBarValues: setValues,
                buttonBarItems, setButtonBarItems,
                statuses, setStatuses, accountRoles, setAccountRoles
            }}
        />
    );
}
