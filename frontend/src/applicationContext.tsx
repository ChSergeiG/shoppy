import React from "react";
import type {AlertColor} from "@mui/material";
import type {IApplicationContext} from "../types/IApplicationContextType";
import type {IStatus} from "../types/IStatus";
import type {IAccountRole} from "../types/IAccountRole";
import type {IButtonBarItem} from "./components/ButtonBar";

export const ApplicationContext = React.createContext<IApplicationContext>({});

export const ApplicationContextProvider: React.FC<React.PropsWithChildren<{}>> = (props) => {

    const [adminFilter, setAdminFilter] = React.useState<string>("");

    const [buttonBarItems, setButtonBarItems] = React.useState<IButtonBarItem[]>([]);

    return (
        <ApplicationContext.Provider
            children={props.children}
            value={{
                adminFilter, setAdminFilter,
                buttonBarItems, setButtonBarItems,
            }}
        />
    );
}
