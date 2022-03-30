import type {AlertColor} from "@mui/material";
import type {IStatus} from "./IStatus";
import type {IAccountRole} from "./IAccountRole";
import type {IButtonBarItem} from "../src/components/ButtonBar";

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

    // button bar
    buttonBarItems?: IButtonBarItem[];
    setButtonBarItems?: (_: IButtonBarItem[]) => void;

    // enums
    statuses: IStatus[];
    setStatuses?: (_: IStatus[]) => void;
    accountRoles: IAccountRole[];
    setAccountRoles?: (_: IAccountRole[]) => void;

};
