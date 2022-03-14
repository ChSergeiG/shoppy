import type {IStatus} from "./IStatus";

export type IGood = {
    id: number | undefined;
    name: string;
    article: number | undefined;
    status: IStatus;
};

export type IOrder = {
    id: number | undefined;
    info: string;
    status: IStatus;
};

export type IAccount = {
    id: number | undefined;
    name: string;
    password: string;
    status: IStatus;
};

export type IAdminContent = IGood | IOrder | IAccount

export type IAdminTableRow = {
    // table row number
    number: number | undefined;
    // unique key to identify row
    key: string;
    // which object this row accords
    content: IAdminContent | undefined;
    // rendered view of content object
    renderedContent: any;
};

export type IAdminTableState = {
    isLoading: boolean;
    statuses: IStatus[];
    rows: IAdminTableRow[];
};

export type IJwtRequest = {
    login: string;
    password: string;
};

export type IJwtResponse = {
    token: string;
    expirationTime: any;
};
