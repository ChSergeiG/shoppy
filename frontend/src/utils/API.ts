import axios, {AxiosInstance, AxiosRequestConfig, AxiosRequestHeaders, AxiosResponse} from "axios";
import type {
    AccountRole,
    AdminAccountDto,
    AdminOrderAccountsDto,
    AdminCountedGoodDto,
    AdminOrderGoodsDto,
    AdminGoodDto,
    AdminOrderDto,
    ExtendedOrderDto,
    JwtRequestDto,
    JwtResponseDto,
    Status
} from "../types";
import {authorizationStore} from "../store/UserAuthorizationStore";
import {} from "../types";

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
    return client().get(
        url,
        {
            ...config,
            headers: resolveHeaders(config?.headers)
        }
    );
};

const put = <RQ, RS>(url: string, data: RQ, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().put(
        url,
        data,
        {
            ...config,
            headers: resolveHeaders(config?.headers)
        }
    );
};

const post = <RQ, RS>(url: string, data: RQ, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().post(
        url,
        data,
        {
            ...config,
            headers: resolveHeaders(config?.headers)
        }
    );
};

const deleteRq = <RQ, RS>(url: string, data: RQ, config: AxiosRequestConfig<RQ> = {}): Promise<AxiosResponse<RS>> => {
    return client().delete(
        url,
        {
            ...config,
            data: data,
            headers: resolveHeaders(config?.headers)
        }
    );
};

const resolveHeaders = (
    headers?: AxiosRequestHeaders
): AxiosRequestHeaders | undefined => {
    if (headers?.["X-Authorization"]) {
        return headers;
    } else {
        return {
            ...headers,
            "X-Authorization": authStore().token || ""
        };
    }
};

/////////////////////
// AdminController //
/////////////////////

export const addAccountInAdminArea = (
    accountToCreate: AdminAccountDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminAccountDto>> => post(
    "/admin/account/add",
    accountToCreate,
    config
);

export const addDefaultAccountInAdminArea = (
    login: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminAccountDto>> => put(
    `/admin/account/${login}`,
    {},
    config
);

export const addDefaultOrderInAdminArea = (
    info: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderDto>> => put(
    `/admin/order/${info}`,
    {},
    config
);

export const addGoodInAdminArea = (
    data: AdminGoodDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto>> => post(
    "/admin/good/add",
    data,
    config
);

export const addOrderInAdminArea = (
    data: AdminOrderDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderDto>> => post(
    "/admin/order/add",
    data,
    config
);

export const deleteAccountInAdminAreaByLogin = (
    login?: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<string>> => deleteRq(
    `/admin/account/${login}`,
    {},
    config
);

export const deleteGoodInAdminAreaByArticle = (
    article?: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<string>> => deleteRq(
    `/admin/good/${article}`,
    {},
    config
);

export const deleteOrderInAdminAreaById = (
    id?: number,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<string>> => deleteRq(
    `/admin/order/${id}`,
    {},
    config
);

export const getAccountInAdminAreaByLogin = (
    login?: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminAccountDto>> => get(
    `/admin/account/${login}`,
    config
)

export const getAccountsInAdminAreaByOrderId = (
    id?: number,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminAccountDto[]>> => get(
    `/admin/order/accounts/${id}`,
    config
);

export const getAccountsInAdminAreaByOrderIds = (
    ids: number[],
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderAccountsDto[]>> => post(
    "/admin/order/accounts",
    ids,
    config
);

export const getAllAccountsInAdminArea = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminAccountDto[]>> => get(
    "/admin/account/get_all",
    config
);

export const getAllGoodsInAdminArea = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto[]>> => get(
    "/admin/good/get_all",
    config
);

export const getAllOrdersInAdminArea = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderDto[]>> => get(
    "/admin/order/get_all",
    config
);

export const getGoodInAdminAreaById = (
    id?: number,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto>> => get(
    `/admin/good/${id}`,
    config
);

export const getGoodsInAdminAreaByOrderId = (
    id?: number,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminCountedGoodDto>> => get(
    `/admin/order/goods/${id}`,
    config
);

export const getGoodsInAdminAreaByOrderIds = (
    ids: number[],
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderGoodsDto[]>> => post(
    `/admin/order/goods`,
    ids,
    config
);

export const getOrderInAdminAreaById = (
    id?: number,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderDto>> => get(
    `/admin/order/${id}`,
    config
);

export const putDefaultGoodInAdminArea = (
    name: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto>> => put(
    `/admin/good/${name}`,
    {},
    config
);

export const updateAccountInAdminArea = (
    accountToUpdate: AdminAccountDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminAccountDto>> => post(
    "/admin/account/update",
    accountToUpdate,
    config
);

export const updateGoodInAdminArea = (
    goodToUpdate: AdminGoodDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto>> => post(
    "/admin/good/update",
    goodToUpdate,
    config
);

export const updateOrderInAdminArea = (
    orderToUpdate: AdminOrderDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminOrderDto>> => post(
    "/admin/order/update",
    orderToUpdate,
    config
);

//////////////////////
// CommonController //
//////////////////////

export const getAllRoles = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AccountRole[]>> => get(
    "/common/enum/account-roles",
    config
);

export const getAllStatuses = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<Status[]>> => get(
    "/common/enum/statuses",
    config
);

/////////////////////
// GoodsController //
/////////////////////

export const getAllGoodsUsingFilterAndPagination = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto[]>> => get(
    "/goods/get_all",
    config
);

export const getGoodsByIds = (
    data: number[],
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AdminGoodDto[]>> => post(
    "/goods/get_by_id",
    data,
    config
);

/////////////////////////////////
// JwtAuthenticationController //
/////////////////////////////////

export const doLogin = (
    data: JwtRequestDto,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<JwtResponseDto>> => post(
    "/jwt/login",
    data,
    config
);

export const logout = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<string>> => get(
    "/jwt/logout",
    config
);

export const probeToken = (
    token: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<boolean>> => get(
    "/jwt/probe",
    config
);

/////////////////////
// OrderController //
/////////////////////

export const createOrder = (
    data: AdminGoodDto[],
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<void>> => post(
    "/orders/create",
    data,
    config
);

export const getOrderInfoByGuid = (
    url: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<ExtendedOrderDto>> => get(
    url,
    config
);

////////////////////
// UserController //
////////////////////

export const getUserRoles = (
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AccountRole[]>> => post(
    "/user/roles",
    config
);

export const getUserRolesByLogin = (
    login: string,
    config: AxiosRequestConfig = {}
): Promise<AxiosResponse<AccountRole[]>> => get(
    `user/roles/${login}`,
    config
);

