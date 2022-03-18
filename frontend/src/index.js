import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import App from "./App"
import {AdminPage} from "./pages/admin/admin.page";
import {AccountsTable, GoodsTable, OrdersTable} from "./pages/admin";
import {ApplicationContextProvider} from "./applicationContext";
import {ShopSnackBar} from "./components/ShopSnackBar";

ReactDOM.render(
    <ApplicationContextProvider>
        <ShopSnackBar/>
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
    </ApplicationContextProvider>,
    document.getElementById('root')
)
