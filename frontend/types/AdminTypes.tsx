import type {IStatus} from "./IStatus";
import type {IResponseType} from "./IResponseType";
import type {IAccountRole} from "./IAccountRole";

export type IGood = {
    id: number | undefined;
    name: string;
    price: number;
    article: string | undefined;
    status: IStatus;
};

export type IOrder = {
    id: number | undefined;
    info: string;
    status: IStatus;
};

export type IAccount = {
    id: number | undefined;
    login: string;
    password: string;
    salted: boolean;
    status: IStatus;
    accountRoles: IAccountRole[];
};

export type IAdminContent = IGood | IOrder | IAccount;

export type IAdminTableState<T extends IAdminContent> = {
    isLoading: boolean;
    statuses: IStatus[];
    accountRoles: IAccountRole[];
    rows: T[];
    sortBy: string;
    sortDirection?: boolean;
};

export type IAdminTableProps<T extends IAdminContent> = {
    getDataCallback: () => Promise<IResponseType<T[]>>;
    columns: number;
    newEntityCreator?: () => T;
    createCallback: (entity: T) => Promise<IResponseType<T>>;
    updateCallback: (entity: T) => Promise<IResponseType<T>>;
    deleteCallback: (entity: T) => Promise<IResponseType<{}>>;
    refreshCallback: (entity: T) => Promise<IResponseType<T> | undefined>;
};

export type IJwtRequest = {
    login: string;
    password: string;
};

export type IJwtResponse = {
    token: string;
    expirationTime: any;
};
