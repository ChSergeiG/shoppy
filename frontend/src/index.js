import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import AdminPage from "./pages/admin/admin.page";
import {ShopSnackBar} from "./components/ShopSnackBar";
import MainPage from "./pages/MainPage";
import ButtonBar from "./components/ButtonBar";
import {refreshStatics} from "./store/StaticsStore";

refreshStatics();

ReactDOM.render(
    <>
        <ShopSnackBar/>
        <BrowserRouter>
            <ButtonBar>
                <Routes>
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/admin">
                        <Route path=":table" element={<AdminPage/>}/>
                    </Route>
                </Routes>
            </ButtonBar>
        </BrowserRouter>
    </>,
    document.getElementById('root')
)
