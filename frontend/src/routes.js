import React from 'react';
import {Route} from 'react-router';

import App from './App';
import MainPage from './components/pages/MainPage';
import AdminPage from './components/pages/AdminPage';

/**
 * All routes go here.
 * Don't forget to import the components above after adding new route.
 */
export default (
    <Route path="/" component={App}>
        <Route component={MainPage}/>
        <Route path="/admin" component={AdminPage}/>
    </Route>
);
