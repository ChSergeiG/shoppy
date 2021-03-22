import React from 'react';
import {Switch} from 'react-router-dom';
import {CustomRoute} from './component/custom-route';

const Routes = () => {
    return (
        <div className="routes-wrapper">
            <Switch>
                <CustomRoute path="/123" setActiveLink={() => {
                }}/>
            </Switch>
        </div>
    );
};

export default Routes;
