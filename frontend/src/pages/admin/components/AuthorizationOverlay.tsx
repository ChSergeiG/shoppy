import React, {useContext, useEffect, useState} from "react";
import {Button, Dialog, DialogContent, DialogTitle, TextField} from "@mui/material";
import {postLogin} from "../../../utils/API";
import {ApplicationContext, verifyAuthorization} from "../../../applicationContext";

type AuthorizationOverlayState = {
    login: string;
    password: string;
};

export const LOCAL_STORAGE_JWT_KEY = "LOCAL_STORAGE_JWT_KEY";

const AuthorizationOverlay: React.FC = () => {

    const context = useContext(ApplicationContext);

    const [state, setState] = useState<AuthorizationOverlayState>({
        login: "",
        password: "",
    });

    useEffect(() => {
        verifyAuthorization(context).then(_ => {
        });
    }, [context.token]);

    const handleChange = ({target: {name, value}}: React.ChangeEvent<HTMLInputElement>) => {
        setState({...state, [name]: value});
    }

    const handleSubmit = async (e: any) => {
        const tokenResponse = await postLogin({...state})
            .catch((r) => {
                context.setAuthorized?.(false);
                context.setSnackBarValues?.({message: new Date() + " " + r.response.data, color: "error"});
            });
        if (tokenResponse !== undefined) {
            context.setToken?.(tokenResponse.data.token);
        }
        e.preventDefault();
    }

    return (
        <Dialog
            id="login-dialog"
            aria-labelledby="login-dialog"
            open={!context.authorized}
        >
            <DialogTitle
                id="login-dialog-title"
            >
                Enter login info
            </DialogTitle>
            <DialogContent>
                <TextField
                    label="Login"
                    name="login"
                    onChange={handleChange}
                    value={state.login}
                    variant="outlined"
                    fullWidth
                />
                <TextField
                    label="Password"
                    name="password"
                    type={"password"}
                    onChange={handleChange}
                    value={state.password}
                    variant="outlined"
                    fullWidth
                />
                <Button
                    color="primary"
                    type="submit"
                    variant="contained"
                    disabled={!state.login || !state.password}
                    onClick={handleSubmit}
                >
                    Login
                </Button>
            </DialogContent>
        </Dialog>
    );
};

export default AuthorizationOverlay;
