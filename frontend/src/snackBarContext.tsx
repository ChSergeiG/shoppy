import React from "react";
import type {AlertColor} from "@mui/material";

export type ISnackBarContext = {
    message: string;
    color: AlertColor;
    setValues?: (ff: { message: string, color: AlertColor }) => void;
}

export const SnackBarContext = React.createContext<ISnackBarContext>({
    message: "",
    color: "success"
});

export const SnackBarProvider: React.FC<React.PropsWithChildren<{}>> = (props) => {

    const [message, setMessage] = React.useState<string>("");
    const [color, setColor] = React.useState<AlertColor>("success");

    const setValues: ISnackBarContext["setValues"] = ({message, color}) => {
        setMessage(message);
        setColor(color);
    };

    return (
        <SnackBarContext.Provider
            children={props.children}
            value={{message, color, setValues}}
        />
    );
}
