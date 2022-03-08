import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import App from "./App"
import Goods from "./routes/admin/goods";
import Orders from "./routes/admin/orders";
import Users from "./routes/admin/users";

ReactDOM.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App/>}/>
            <Route path="/admin/goods" element={<Goods/>}/>
            <Route path="/admin/orders" element={<Orders/>}/>
            <Route path="/admin/users" element={<Users/>}/>
        </Routes>
    </BrowserRouter>,
    document.getElementById('root')
)
