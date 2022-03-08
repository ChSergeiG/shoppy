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

export type IUser = {
    id: number | undefined;
    name: string;
    password: string;
    status: IStatus;
};

export type IAdminContent =  IGood | IOrder | IUser

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
