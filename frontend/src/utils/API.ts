import axios, {AxiosInstance, AxiosResponse} from "axios";
import type {IAccount, IGood, IOrder} from "../../types/AdminTypes";
import type {IStatus} from "../../types/IStatus";

export default function client(): AxiosInstance {
    return axios.create({
        baseURL: 'http://localhost:8080',
        responseType: 'json'
    });
}

//////////////////////////
// admin/AccountController //
//////////////////////////

export async function getAccounts(): Promise<AxiosResponse<IAccount[]>> {
    return client()
        .get("/admin/account/get_all");
}

export async function createNewDefaultAccount(login: string): Promise<AxiosResponse<void>> {
    return client()
        .put(`/admin/account/${login}`);
}

export async function createNewAccount(accountToCreate: IAccount): Promise<AxiosResponse<void>> {
    return client()
        .post("/admin/account/add", accountToCreate);
}

export async function updateExistingAccount(accountToUpdate: IAccount): Promise<AxiosResponse<string>> {
    return client()
        .post("/admin/account/update", accountToUpdate);
}

export async function deleteExistingAccount(accountToDelete: IAccount): Promise<AxiosResponse> {
    return client()
        .delete(`/admin/account/${accountToDelete.name}`);
}

//////////////////////////
// admin/GoodController //
//////////////////////////

export async function getGoods(): Promise<AxiosResponse<IGood[]>> {
    return client()
        .get("/admin/good/get_all");
}

export async function createNewDefaultGood(name: string): Promise<AxiosResponse<IGood>> {
    return client()
        .put(`/admin/good/${name}`);
}

export async function createNewGood(goodToCreate: IGood): Promise<AxiosResponse<IGood>> {
    return client()
        .post("/admin/good/add", goodToCreate);
}

export async function updateExistingGood(goodToUpdate: IGood): Promise<AxiosResponse<IGood>> {
    return client()
        .post("/admin/good/update", goodToUpdate);
}

export async function deleteExistingGood(goodToDelete: IGood): Promise<AxiosResponse<number>> {
    return client()
        .delete(`/admin/good/${goodToDelete.article}`);
}

///////////////////////////
// admin/OrderController //
///////////////////////////

export async function getOrders(): Promise<AxiosResponse<IOrder[]>> {
    return client()
        .get("/admin/order/get_all");
}

export async function createNewDefaultOrder(info: string): Promise<AxiosResponse<void>> {
    return client()
        .put(`/admin/order/${info}`);
}

export async function createNewOrder(orderToCreate: IOrder): Promise<AxiosResponse<void>> {
    return client()
        .post("/admin/order/add", orderToCreate);
}

export async function updateExistingOrder(orderToUpdate: IOrder): Promise<AxiosResponse<string>> {
    return client()
        .post("/admin/order/update", orderToUpdate);
}

export async function deleteExistingOrder(orderToDelete: IOrder): Promise<AxiosResponse<void>> {
    return client()
        .delete(`/admin/order/${orderToDelete.id}`);
}

//////////////////////
// CommonController //
//////////////////////

export async function getStatuses(): Promise<AxiosResponse<IStatus[]>> {
    return client().get("/statuses");
}
