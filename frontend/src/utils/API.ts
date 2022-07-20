import axios, {AxiosInstance, AxiosRequestConfig, AxiosRequestHeaders, AxiosResponse} from "axios";
import type {IAccount, IGood, IJwtRequest, IJwtResponse, IOrder} from "../../types/AdminTypes";
import type {IStatus} from "../../types/IStatus";
import type {IResponseType} from "../../types/IResponseType";
import type {IAccountRole} from "../../types/IAccountRole";
import type {IPage} from "../../types/IPage";
import {authorizationStore} from "../store/UserAuthorizationStore";

export function client(): AxiosInstance {
    return axios.create({
        baseURL: "/api",
        responseType: "json"
    });
}

function authStore() {
    return authorizationStore.getState();
}

const get = <RQ, RS>(url: string, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().get(url, {
        ...config,
        headers: resolveHeaders(config.headers)
    });
};

const put = <RQ, RS>(url: string, data: RQ, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().put(url, data, {
        ...config,
        headers: resolveHeaders(config.headers)
    });
};

const post = <RQ, RS>(url: string, data: RQ, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().post(url, data, {
        ...config,
        headers: resolveHeaders(config.headers)
    });
};

const deleteRq = <RQ, RS>(url: string, data: RQ, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().delete(url, {
        ...config,
        data: data,
        headers: resolveHeaders(config.headers)
    });
};

const resolveHeaders =
    (headers?: AxiosRequestHeaders): AxiosRequestHeaders | undefined => {
        if (headers?.["X-Authorization"]) {
            return headers;
        } else {
            return {
                ...headers,
                "X-Authorization": authStore().token || ""
            };
        }
    };

/////////////////////////////
// admin/AccountController //
/////////////////////////////

export const getAccounts =
    async (): Promise<IResponseType<IAccount[]>> =>
        get("/admin/account/get_all");

export const getAccount =
    async (login: string): Promise<IResponseType<IAccount>> =>
        get(`/admin/account/${login}`)

export const createNewDefaultAccount =
    async (login: string): Promise<IResponseType<void>> =>
        put(`/admin/account/${login}`, {});

export const createNewAccount =
    async (accountToCreate: IAccount): Promise<IResponseType<IAccount>> =>
        post("/admin/account/add", accountToCreate);

export const updateExistingAccount =
    (accountToUpdate: IAccount): Promise<IResponseType<IAccount>> =>
        post("/admin/account/update", accountToUpdate);

export const deleteExistingAccount =
    (accountToDelete: IAccount): Promise<IResponseType<number>> =>
        deleteRq(`/admin/account/${accountToDelete.login}`, {});

//////////////////////////
// admin/GoodController //
//////////////////////////

export const getGoods =
    (): Promise<AxiosResponse<IGood[]>> =>
        get("/admin/good/get_all");

export const getGood =
    (id: number | undefined): Promise<AxiosResponse<IGood> | undefined> =>
        get(`/admin/good/${id}`);

export const createNewDefaultGood =
    (name: string): Promise<AxiosResponse<IGood>> =>
        put(`/admin/good/${name}`, {});

export const createNewGood =
    (goodToCreate: IGood): Promise<AxiosResponse<IGood>> =>
        post("/admin/good/add", goodToCreate);

export const updateExistingGood =
    (goodToUpdate: IGood): Promise<AxiosResponse<IGood>> =>
        post("/admin/good/update", goodToUpdate);

export const deleteExistingGood =
    (goodToDelete: IGood): Promise<AxiosResponse<number>> =>
        deleteRq(`/admin/good/${goodToDelete.article}`, {});

///////////////////////////
// admin/OrderController //
///////////////////////////

export const getOrders =
    (): Promise<AxiosResponse<IOrder[]>> =>
        get("/admin/order/get_all");

export const getOrder =
    (id: number | undefined): Promise<AxiosResponse<IOrder> | undefined> =>
        get(`/admin/order/${id}`);

export const createNewDefaultOrder =
    (info: string): Promise<AxiosResponse<IOrder>> =>
        put(`/admin/order/${info}`, {});

export const createNewOrder =
    (orderToCreate: IOrder): Promise<AxiosResponse<IOrder>> =>
        post("/admin/order/add", orderToCreate);

export const updateExistingOrder =
    (orderToUpdate: IOrder): Promise<AxiosResponse<IOrder>> =>
        post("/admin/order/update", orderToUpdate);

export const deleteExistingOrder =
    (orderToDelete: IOrder): Promise<AxiosResponse<number>> =>
        deleteRq(`/admin/order/${orderToDelete.id}`, {});

export const getAccountsByOrderId =
    (orderToGetInfo: IOrder): Promise<AxiosResponse<IAccount[]>> =>
        get(`/admin/order/accounts/${orderToGetInfo.id}`);

export const getGoodsByOrderId =
    (orderToGetInfo: IOrder): Promise<AxiosResponse<(IGood & { count: number })[]>> =>
        get(`/admin/order/goods/${orderToGetInfo.id}`);

/////////////////////////////////
// common/CommonGoodController //
/////////////////////////////////

export const getAllGoods = (
    filter?: string,
    page?: number,
    pageSize?: number
): Promise<AxiosResponse<IPage<IGood>>> => {
    const params: Record<string, string | number> = {}
    if (filter && filter.trim().length !== 0) {
        params["filter"] = filter.trim();
    }
    if (page) {
        params["page"] = page;
    }
    if (pageSize) {
        params["size"] = pageSize;
    }
    return get("/goods/get_all", {params})
}

export const getGoodsByIds =
    (ids: number[]): Promise<AxiosResponse<IGood[]>> =>
        post("/goods/get_by_id", ids);


//////////////////////////////////
// common/CommonOrderController //
//////////////////////////////////

export const postOrder =
    (body: IGood[]): Promise<AxiosResponse<string>> =>
        post(`/orders/create`, body);

export const getCreatedOrderInfo =
    (url: string): Promise<AxiosResponse<IOrder & { guid: string, goods: { good: IGood, count: number }[] }>> =>
        get(url);

/////////////////////////////////////
// jwt/JwtAuthenticationController //
/////////////////////////////////////

export const postLogin =
    (loginRequest: IJwtRequest): Promise<AxiosResponse<IJwtResponse>> =>
        post("/jwt/login", loginRequest);

export const getProbeLogin =
    (token: string): Promise<AxiosResponse<boolean>> =>
        get("/jwt/probe", {headers: {"X-Authorization": token}});

export const getLogout =
    (): Promise<AxiosResponse<void>> =>
        get("/jwt/logout");


//////////////////////
// CommonController //
//////////////////////

export const getAccountRoles =
    (): Promise<AxiosResponse<IAccountRole[]>> =>
        get("/common/enum/account-roles");

export const getStatuses =
    (): Promise<AxiosResponse<IStatus[]>> =>
        get("/common/enum/statuses");
