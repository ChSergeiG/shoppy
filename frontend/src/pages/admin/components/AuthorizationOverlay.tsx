import React, {useState} from "react";
import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import {doLogin} from "../../../utils/API";
import {Link} from "react-router-dom";
import {
    authorizationStore,
    IAuthorizationStore,
    updateAuthorizationState,
    updateAuthorizationToken,
    verifyAuthorization
} from "../../../store/UserAuthorizationStore";
import {useStore} from "effector-react";
import {placeSnackBarAlert} from "../../../store/SnackBarStore";

type AuthorizationOverlayState = {
    login: string;
    password: string;
};

export const LOCAL_STORAGE_JWT_KEY = "LOCAL_STORAGE_JWT_KEY";

const AuthorizationOverlay: React.FC = () => {

    const authStore = useStore<IAuthorizationStore>(authorizationStore);

    const [state, setState] = useState<AuthorizationOverlayState>({
        login: "",
        password: "",
    });

    const handleChange = ({target: {name, value}}: React.ChangeEvent<HTMLInputElement>) => {
        setState({...state, [name]: value});
    }

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        const tokenResponse = await doLogin({...state})
            .catch((r) => {
                updateAuthorizationState(false);
                placeSnackBarAlert({message: r.response.data, color: "error"});
            });
        if (tokenResponse !== undefined) {
            updateAuthorizationToken(tokenResponse.data.token || "");
            verifyAuthorization();
        }
    }

    return (
        <Dialog
            id="login-dialog"
            aria-labelledby="login-dialog"
            open={!authStore.authorized}
        >
            <form
                onSubmit={handleSubmit}
                autoComplete="off"
            >
                <DialogTitle
                    id="login-dialog-title"
                >
                    Enter login info
                </DialogTitle>
                <DialogContent
                    sx={{
                        m: "10px",
                    }}
                >
                    <TextField
                        label="Login"
                        name="login"
                        onChange={handleChange}
                        value={state.login}
                        sx={{
                            m: "6px",
                        }}
                        variant="outlined"
                        fullWidth
                    />
                    <TextField
                        label="Password"
                        name="password"
                        type={"password"}
                        onChange={handleChange}
                        sx={{
                            m: "6px",
                        }}
                        value={state.password}
                        variant="outlined"
                        fullWidth
                    />
                </DialogContent
                >
                <DialogActions>
                    <Button
                        color="secondary"
                        type="button"
                        variant="outlined"
                    >
                        <Link
                            to="/"
                            style={{textDecoration: "none"}}
                        >
                            Cancel
                        </Link>
                    </Button>
                    <Button
                        color="primary"
                        type="submit"
                        variant="contained"
                        disabled={!state.login || !state.password}
                        onClick={handleSubmit}
                    >
                        Login
                    </Button>
                </DialogActions>
            </form>
        </Dialog>
    );
};

export default AuthorizationOverlay;
