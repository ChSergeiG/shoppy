import type {AlertColor} from "@mui/material";
import type {IStatus} from "./IStatus";
import type {IAccountRole} from "./IAccountRole";

export type IApplicationContext = {
    // authorization
    token: string;
    setToken?: (token: string) => void;
    authorized: boolean;
    setAuthorized?: (authorized: boolean) => void;

    // admin filtering
    adminFilter?: string;
    setAdminFilter?: (filter: string) => void;

    // snackbar
    message: string;
    color: AlertColor;
    setSnackBarValues?: (_: { message: string, color: AlertColor }) => void;

    // enums
    statuses?: IStatus[];
    accountRoles?: IAccountRole[];

};
