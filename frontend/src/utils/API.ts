import axios, {AxiosInstance, AxiosResponse} from "axios";
import type {IGood, IOrder, IUser} from "../../types/AdminTypes";
import type {IStatus} from "../../types/IStatus";

export default function client(): AxiosInstance {
    return axios.create({
        baseURL: 'http://localhost:8080',
        responseType: 'json'
    });
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
        .put(`/admin/good/$name`);
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
        .put(`/admin/order/$info`);
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

//////////////////////////
// admin/UserController //
//////////////////////////


export async function getUsers(): Promise<AxiosResponse<IUser[]>> {
    return client()
        .get("/admin/user/get_all");
}

export async function createNewDefaultUser(name: string): Promise<AxiosResponse<void>> {
    return client()
        .put(`/admin/user/$name`);
}

export async function createNewUser(userToCreate: IUser): Promise<AxiosResponse<void>> {
    return client()
        .post("/admin/user/add", userToCreate);
}

export async function updateExistingUser(userToUpdate: IUser): Promise<AxiosResponse<string>> {
    return client()
        .post("/admin/user/update", userToUpdate);
}

export async function deleteExistingUser(userToDelete: IUser): Promise<AxiosResponse> {
    return client()
        .delete(`/admin/user/${userToDelete.name}`);
}

//////////////////////
// CommonController //
//////////////////////

export async function getStatuses(): Promise<AxiosResponse<IStatus[]>> {
    return client().get("/statuses");
}
