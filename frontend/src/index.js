import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import App from "./App"
import Accounts from "./routes/admin/accounts";
import Goods from "./routes/admin/goods";
import Orders from "./routes/admin/orders";

ReactDOM.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App/>}/>
            <Route path="/admin">
                <Route path="accounts" element={<Accounts/>}/>
                <Route path="goods" element={<Goods/>}/>
                <Route path="orders" element={<Orders/>}/>
            </Route>
        </Routes>
    </BrowserRouter>,
    document.getElementById('root')
)
