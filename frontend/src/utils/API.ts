import axios, {AxiosInstance, AxiosResponse} from "axios";
import type {IAccount, IGood, IJwtRequest, IJwtResponse, IOrder} from "../../types/AdminTypes";
import type {IStatus} from "../../types/IStatus";
import type {IResponseType} from "../../types/IResponseType";
import type {IApplicationContext} from "../../types/IApplicationContextType";
import type {IAccountRole} from "../../types/IAccountRole";

function getApiBaseUrl() : string {
    const envValue = process.env["REACT_APP_API_BASE_URL"]?.trim();
    if (envValue !== undefined && envValue.length > 0) {
        return  envValue;
    }
    return "SUBSTITUTE_API_URL";
}

function client(): AxiosInstance {
    return axios.create({
        baseURL: getApiBaseUrl(),
        responseType: "json"
    });
}

/////////////////////////////
// admin/AccountController //
/////////////////////////////

export async function getAccounts(context: IApplicationContext): Promise<IResponseType<IAccount[]>> {
    return client()
        .get("/admin/account/get_all", {headers: {"X-Authorization": context.token || ""}});
}

export async function getAccount(context: IApplicationContext, login: string): Promise<IResponseType<IAccount>> {
    return client()
        .get(`/admin/account/${login}`, {headers: {"X-Authorization": context.token || ""}});
}

export async function createNewDefaultAccount(context: IApplicationContext, login: string): Promise<IResponseType<void>> {
    return client()
        .put(`/admin/account/${login}`, {}, {headers: {"X-Authorization": context.token || ""}});
}

export async function createNewAccount(context: IApplicationContext, accountToCreate: IAccount): Promise<IResponseType<IAccount>> {
    return client()
        .post("/admin/account/add", accountToCreate, {headers: {"X-Authorization": context.token || ""}});
}

export async function updateExistingAccount(context: IApplicationContext, accountToUpdate: IAccount): Promise<IResponseType<IAccount>> {
    return client()
        .post("/admin/account/update", accountToUpdate, {headers: {"X-Authorization": context.token || ""}});
}

export async function deleteExistingAccount(context: IApplicationContext, accountToDelete: IAccount): Promise<IResponseType<number>> {
    return client()
        .delete(`/admin/account/${accountToDelete.login}`, {headers: {"X-Authorization": context.token || ""}});
}

//////////////////////////
// admin/GoodController //
//////////////////////////

export async function getGoods(context: IApplicationContext): Promise<AxiosResponse<IGood[]>> {
    return client()
        .get("/admin/good/get_all", {headers: {"X-Authorization": context.token || ""}});
}

export async function getGood(context: IApplicationContext, id: number | undefined): Promise<AxiosResponse<IGood> | undefined> {
    return client()
        .get(`/admin/good/${id}`, {headers: {"X-Authorization": context.token || ""}});
}

export async function createNewDefaultGood(context: IApplicationContext, name: string): Promise<AxiosResponse<IGood>> {
    return client()
        .put(`/admin/good/${name}`, {}, {headers: {"X-Authorization": context.token || ""}});
}

export async function createNewGood(context: IApplicationContext, goodToCreate: IGood): Promise<AxiosResponse<IGood>> {
    return client()
        .post("/admin/good/add", goodToCreate, {headers: {"X-Authorization": context.token || ""}});
}

export async function updateExistingGood(context: IApplicationContext, goodToUpdate: IGood): Promise<AxiosResponse<IGood>> {
    return client()
        .post("/admin/good/update", goodToUpdate, {headers: {"X-Authorization": context.token || ""}});
}

export async function deleteExistingGood(context: IApplicationContext, goodToDelete: IGood): Promise<AxiosResponse<number>> {
    return client()
        .delete(`/admin/good/${goodToDelete.article}`, {headers: {"X-Authorization": context.token || ""}});
}

///////////////////////////
// admin/OrderController //
///////////////////////////

export async function getOrders(context: IApplicationContext): Promise<AxiosResponse<IOrder[]>> {
    return client()
        .get("/admin/order/get_all", {headers: {"X-Authorization": context.token || ""}});
}

export async function getOrder(context: IApplicationContext, id: number | undefined): Promise<AxiosResponse<IOrder> | undefined> {
    return client()
        .get(`/admin/order/${id}`, {headers: {"X-Authorization": context.token || ""}});
}


export async function createNewDefaultOrder(context: IApplicationContext, info: string): Promise<AxiosResponse<IOrder>> {
    return client()
        .put(`/admin/order/${info}`, {}, {headers: {"X-Authorization": context.token || ""}});
}

export async function createNewOrder(context: IApplicationContext, orderToCreate: IOrder): Promise<AxiosResponse<IOrder>> {
    return client()
        .post("/admin/order/add", orderToCreate, {headers: {"X-Authorization": context.token || ""}});
}

export async function updateExistingOrder(context: IApplicationContext, orderToUpdate: IOrder): Promise<AxiosResponse<IOrder>> {
    return client()
        .post("/admin/order/update", orderToUpdate, {headers: {"X-Authorization": context.token || ""}});
}

export async function deleteExistingOrder(context: IApplicationContext, orderToDelete: IOrder): Promise<AxiosResponse<number>> {
    return client()
        .delete(`/admin/order/${orderToDelete.id}`, {headers: {"X-Authorization": context.token || ""}});
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
