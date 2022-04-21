import axios, {AxiosInstance, AxiosResponse} from "axios";
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

/////////
// mvc //
/////////

export async function logoutRequest(): Promise<void> {
    return client()
        .get("/logout")
}

/////////////////////////////
// admin/AccountController //
/////////////////////////////

export async function getAccounts(): Promise<IResponseType<IAccount[]>> {
    return client()
        .get("/admin/account/get_all", {headers: {"X-Authorization": authStore().token || ""}});
}

export async function getAccount(login: string): Promise<IResponseType<IAccount>> {
    return client()
        .get(`/admin/account/${login}`, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function createNewDefaultAccount(login: string): Promise<IResponseType<void>> {
    return client()
        .put(`/admin/account/${login}`, {}, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function createNewAccount(accountToCreate: IAccount): Promise<IResponseType<IAccount>> {
    return client()
        .post("/admin/account/add", accountToCreate, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function updateExistingAccount(accountToUpdate: IAccount): Promise<IResponseType<IAccount>> {
    return client()
        .post("/admin/account/update", accountToUpdate, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function deleteExistingAccount(accountToDelete: IAccount): Promise<IResponseType<number>> {
    return client()
        .delete(`/admin/account/${accountToDelete.login}`, {headers: {"X-Authorization": authStore().token || ""}});
}

//////////////////////////
// admin/GoodController //
//////////////////////////

export async function getGoods(): Promise<AxiosResponse<IGood[]>> {
    return client()
        .get("/admin/good/get_all", {headers: {"X-Authorization": authStore().token || ""}});
}

export async function getGood(id: number | undefined): Promise<AxiosResponse<IGood> | undefined> {
    return client()
        .get(`/admin/good/${id}`, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function createNewDefaultGood(name: string): Promise<AxiosResponse<IGood>> {
    return client()
        .put(`/admin/good/${name}`, {}, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function createNewGood(goodToCreate: IGood): Promise<AxiosResponse<IGood>> {
    return client()
        .post("/admin/good/add", goodToCreate, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function updateExistingGood(goodToUpdate: IGood): Promise<AxiosResponse<IGood>> {
    return client()
        .post("/admin/good/update", goodToUpdate, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function deleteExistingGood(goodToDelete: IGood): Promise<AxiosResponse<number>> {
    return client()
        .delete(`/admin/good/${goodToDelete.article}`, {headers: {"X-Authorization": authStore().token || ""}});
}

///////////////////////////
// admin/OrderController //
///////////////////////////

export async function getOrders(): Promise<AxiosResponse<IOrder[]>> {
    return client()
        .get("/admin/order/get_all", {headers: {"X-Authorization": authStore().token || ""}});
}

export async function getOrder(id: number | undefined): Promise<AxiosResponse<IOrder> | undefined> {
    return client()
        .get(`/admin/order/${id}`, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function createNewDefaultOrder(info: string): Promise<AxiosResponse<IOrder>> {
    return client()
        .put(`/admin/order/${info}`, {}, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function createNewOrder(orderToCreate: IOrder): Promise<AxiosResponse<IOrder>> {
    return client()
        .post("/admin/order/add", orderToCreate, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function updateExistingOrder(orderToUpdate: IOrder): Promise<AxiosResponse<IOrder>> {
    return client()
        .post("/admin/order/update", orderToUpdate, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function deleteExistingOrder(orderToDelete: IOrder): Promise<AxiosResponse<number>> {
    return client()
        .delete(`/admin/order/${orderToDelete.id}`, {headers: {"X-Authorization": authStore().token || ""}});
}

export async function getAccountsByOrderId(orderToGetInfo: IOrder): Promise<AxiosResponse<IAccount[]>> {
    return client()
        .get(`/admin/order/accounts/${orderToGetInfo.id}`, {headers: {"X-Authorization": authStore().token || ""}});

}

export async function getGoodsByOrderId(orderToGetInfo: IOrder): Promise<AxiosResponse<(IGood & { count: number })[]>> {
    return client()
        .get(`/admin/order/goods/${orderToGetInfo.id}`, {headers: {"X-Authorization": authStore().token || ""}});
}

/////////////////////////////////
// common/CommonGoodController //
/////////////////////////////////

export async function getAllGoods(
    filter?: string,
    page?: number,
    pageSize?: number
): Promise<AxiosResponse<IPage<IGood>>> {
    const params: string[] = []
    if (filter && filter.trim().length !== 0) {
        params.push(`filter=${filter.trim()}`);
    }
    if (page !== undefined) {
        params.push(`page=${page}`);
    }
    if (pageSize !== undefined) {
        params.push(`size=${pageSize}`);
    }
    if (params.length > 0) {
        return client()
            .get(`/goods/get_all?${params.join("&")}`);
    } else {
        return client()
            .get(`/goods/get_all`);
    }
}

export async function getGoodsByIds(
    ids: number[]
): Promise<AxiosResponse<IGood[]>> {
    return client()
        .post("/goods/get_by_id", ids, {headers: {"X-Authorization": authStore().token || ""}});
}

//////////////////////////////////
// common/CommonOrderController //
//////////////////////////////////

export async function postOrder(
    body: IGood[]
): Promise<AxiosResponse<string>> {
    return client()
        .post(`/orders/create`, body, {headers: {"X-Authorization": authStore().token || ""}})
}

export async function getCreatedOrderInfo(
    url: string
): Promise<AxiosResponse<IOrder & { guid: string, goods: { good: IGood, count: number }[] }>> {
    return client()
        .get(url, {headers: {"X-Authorization": authStore().token || ""}});
}

/////////////////////////////////////
// jwt/JwtAuthenticationController //
/////////////////////////////////////

export async function postLogin(loginRequest: IJwtRequest): Promise<AxiosResponse<IJwtResponse>> {
    return client()
        .post(`/jwt/login`, loginRequest);
}

export async function getProbeLogin(token: string): Promise<AxiosResponse<boolean>> {
    return client()
        .get("/jwt/probe", {headers: {"X-Authorization": token}})
}

//////////////////////
// CommonController //
//////////////////////

export async function getAccountRoles(): Promise<AxiosResponse<IAccountRole[]>> {
    return client().get("/common/enum/account-roles");
}

export async function getStatuses(): Promise<AxiosResponse<IStatus[]>> {
    return client().get("/common/enum/statuses");
}
