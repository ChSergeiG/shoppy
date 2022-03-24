import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import AdminPage from "./pages/admin/admin.page";
import {ApplicationContextProvider} from "./applicationContext";
import {ShopSnackBar} from "./components/ShopSnackBar";
import Environment from "./components/Environment";
import MainPage from "./pages/MainPage";

ReactDOM.render(
    <ApplicationContextProvider>
        <Environment/>
        <ShopSnackBar/>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MainPage/>}/>
                <Route path="/admin">
                    <Route path=":table" element={<AdminPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    </ApplicationContextProvider>,
    document.getElementById('root')
)
