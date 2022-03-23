import React from "react";
import type {AlertColor} from "@mui/material";
import type {IApplicationContext} from "../types/IApplicationContextType";
import {LOCAL_STORAGE_JWT_KEY} from "./pages/admin/components/AuthorizationOverlay";
import {getProbeLogin} from "./utils/API";
import type {IStatus} from "../types/IStatus";
import type {IAccountRole} from "../types/IAccountRole";

export const ApplicationContext = React.createContext<IApplicationContext>({
    token: "",
    authorized: false,
    message: "",
    color: "success",
    statuses: [],
    accountRoles: [],
});

export const verifyAuthorization = async (context: IApplicationContext): Promise<void> => {
    const storedJwtKey = localStorage.getItem(LOCAL_STORAGE_JWT_KEY);
    if (storedJwtKey) {
        const probeResponse = await getProbeLogin(storedJwtKey);
        if (probeResponse?.data) {
            context.setToken?.(storedJwtKey);
            context.setAuthorized?.(true);
            return;
        }
    }
    const contextToken = context.token;
    if (contextToken) {
        const probeResponse = await getProbeLogin(contextToken);
        if (probeResponse?.data) {
            localStorage.setItem(LOCAL_STORAGE_JWT_KEY, contextToken);
            context.setAuthorized?.(true);
            return;
        }
    }
    context.setToken?.("");
    localStorage.setItem(LOCAL_STORAGE_JWT_KEY, "");
    context.setAuthorized?.(false);
}

export const ApplicationContextProvider: React.FC<React.PropsWithChildren<{}>> = (props) => {

    const [token, setToken] = React.useState<string>("");
    const [authorized, setAuthorized] = React.useState<boolean>(false);
    const [adminFilter, setAdminFilter] = React.useState<string>("");

    const [message, setMessage] = React.useState<string>("");
    const [color, setColor] = React.useState<AlertColor>("success");

    const [statuses, setStatuses] = React.useState<IStatus[]>([]);
    const [accountRoles, setAccountRoles] = React.useState<IAccountRole[]>([]);

    const setValues: IApplicationContext["setSnackBarValues"] = ({message, color}) => {
        setMessage(`[${crypto.randomUUID()}] ${message}`);
        setColor(color);
    };

    return (
        <ApplicationContext.Provider
            children={props.children}
            value={{
                token, setToken,
                authorized, setAuthorized,
                adminFilter, setAdminFilter,
                message, color, setSnackBarValues: setValues,
                statuses, setStatuses, accountRoles, setAccountRoles
            }}
        />
    );
}
