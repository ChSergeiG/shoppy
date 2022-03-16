import type {IStatus} from "./IStatus";
import type {IResponseType} from "./IResponseType";

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
    login: string;
    password: string;
    salted: boolean;
    status: IStatus;
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
    rows: IAdminTableRow<T>[];
};

export type IAbstractAdminProps<T extends IAdminContent> = {
    getDataCallback: () => Promise<IResponseType<T[]>>;
    idExtractor: (data: T) => number | undefined;
    keyExtractor: (data: T) => string;
    headerRowBuilder: () => JSX.Element;
    bodyCellCreator: (
        columnNumber: number,
        data: T,
        statusSelectorCallback: (data: T) => JSX.Element,
        actionsSelectorCallback: (data: T) => JSX.Element
    ) => JSX.Element;
    columns: number;
    newEntityCreator: () => T;
    createCallback: (data: T) => Promise<IResponseType<T>>;
    updateCallback: (data: T) => Promise<IResponseType<T>>;
    deleteCallback: (data: T) => Promise<IResponseType<{}>>;
    refreshCallback?: (data: T) => Promise<IResponseType<T> | undefined>;
};

export type IJwtRequest = {
    login: string;
    password: string;
};

export type IJwtResponse = {
    token: string;
    expirationTime: any;
};
