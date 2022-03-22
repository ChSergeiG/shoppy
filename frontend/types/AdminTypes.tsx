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
    accountRoles: IAccountRole[];
};

export type IAdminContent = IGood | IOrder | IAccount

export type IAbstractAdminState<T extends IAdminContent> = {
    isLoading: boolean;
    statuses: IStatus[];
    accountRoles: IAccountRole[];
    rows: T[];
    filter?: string;
    sortBy: string;
    sortDirection?: boolean;
};

export type IAbstractAdminProps<T extends IAdminContent> = {
    getDataCallback: (context: IApplicationContext) => Promise<IResponseType<T[]>>;
    headerRowBuilder: () => JSX.Element;
    bodyCellCreator: (
        columnNumber: number,
        data: T,
        idCellCallback: (data: T) => JSX.Element,
        statusSelectorCallback: (data: T) => JSX.Element,
        actionsSelectorCallback: (data: T) => JSX.Element,
        accountRoles: IAccountRole[],
        stateUpdater: (entity: T, name: string, ...args: any[]) => void
    ) => JSX.Element;
    columns: number;
    newEntityCreator: () => T;
    createCallback: (context: IApplicationContext, data: T) => Promise<IResponseType<T>>;
    updateCallback: (context: IApplicationContext, data: T) => Promise<IResponseType<T>>;
    deleteCallback: (context: IApplicationContext, data: T) => Promise<IResponseType<{}>>;
    refreshCallback?: (context: IApplicationContext, data: T) => Promise<IResponseType<T> | undefined>;
    filterCallback?: (data: T, filter?: string) => boolean;
};

export type IJwtRequest = {
    login: string;
    password: string;
};

export type IJwtResponse = {
    token: string;
    expirationTime: any;
};
