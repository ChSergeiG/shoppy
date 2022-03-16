import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import App from "./App"
import {AdminPage} from "./pages/admin/admin.page";
import {AccountsTable, GoodsTable, OrdersTable} from "./pages/admin";
import {getProbeLogin, STORED_JWT_TOKEN_KEY, STORED_JWT_TOKEN_VALIDITY_KEY} from "./utils/API";
import {SnackBarContext, SnackBarProvider} from "./snackBarContext";

ReactDOM.render(
    <SnackBarProvider>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}/>
                <Route path="/admin">
                    <Route path="accounts" element={
                        <AdminPage component={
                            <AccountsTable/>
                        }/>
                    }/>
                    <Route path="goods" element={
                        <AdminPage component={
                            <GoodsTable/>
                        }/>
                    }/>
                    <Route path="orders" element={
                        <AdminPage component={
                            <OrdersTable/>
                        }/>
                    }/>
                </Route>
            </Routes>
        </BrowserRouter>
    </SnackBarProvider>,
    document.getElementById('root')
)

setInterval(async () => {
    const token = localStorage.getItem(STORED_JWT_TOKEN_KEY);
    if (token) {
        const validityResponse = await getProbeLogin(token);
        if (validityResponse) {
            localStorage.setItem(STORED_JWT_TOKEN_VALIDITY_KEY, validityResponse.data ? "VALID" : "INVALID");
        }
    } else {
        localStorage.setItem(STORED_JWT_TOKEN_VALIDITY_KEY, "INVALID");
    }
}, 5_000);
