import React, {useEffect} from "react";
import {ApplicationContext} from "../applicationContext";
import {getAccountRoles, getStatuses} from "../utils/API";

const Environment: React.FC = () => {

    const context = React.useContext(ApplicationContext);

    useEffect(() => {
        getStatuses().then(r => context.setStatuses?.(r.data));
        getAccountRoles().then(r => context.setAccountRoles?.(r.data));
    }, []);

    return (<div/>);
}

export default Environment;

