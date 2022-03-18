import type {IStatus} from "./IStatus";
import type {IResponseType} from "./IResponseType";
import type {IApplicationContext} from "./IApplicationContextType";
import type {IAccountRole} from "./IAccountRole";

export type IGood = {
    id: number | undefined;
    name: string;
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
    roles: IAccountRole[];
};

export type IAdminContent = IGood | IOrder | IAccount

/**
 * Represents extended container of entity
 */
export type IAdminTableRow<T> = {
    // table row number
    number: number | undefined;
    // unique key to identify row
    key: string;
    // which object this row accords
    content: T | undefined;
}

export type IAbstractAdminState<T extends IAdminContent> = {
    isLoading: boolean;
    statuses: IStatus[];
    accountRoles: IAccountRole[];
    rows: IAdminTableRow<T>[];
};

export type IAbstractAdminProps<T extends IAdminContent> = {
    getDataCallback: (context: IApplicationContext) => Promise<IResponseType<T[]>>;
    idExtractor: (data: T) => number | undefined;
    keyExtractor: (data: T) => string;
    headerRowBuilder: () => JSX.Element;
    bodyCellCreator: (
        columnNumber: number,
        data: T,
        statusSelectorCallback: (data: T) => JSX.Element,
        actionsSelectorCallback: (data: T) => JSX.Element,
        accountRoles: IAccountRole[]
    ) => JSX.Element;
    columns: number;
    newEntityCreator: () => T;
    createCallback: (context: IApplicationContext, data: T) => Promise<IResponseType<T>>;
    updateCallback: (context: IApplicationContext, data: T) => Promise<IResponseType<T>>;
    deleteCallback: (context: IApplicationContext, data: T) => Promise<IResponseType<{}>>;
    refreshCallback?: (context: IApplicationContext, data: T) => Promise<IResponseType<T> | undefined>;
};

export type IJwtRequest = {
    login: string;
    password: string;
};

export type IJwtResponse = {
    token: string;
    expirationTime: any;
};
