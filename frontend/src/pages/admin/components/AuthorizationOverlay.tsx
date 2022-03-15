import React, {Component} from "react";
import {Alert, Button, Dialog, DialogContent, DialogTitle, TextField} from "@mui/material";
import {getProbeLogin, postLogin, STORED_JWT_TOKEN_KEY, STORED_JWT_TOKEN_VALIDITY_KEY} from "../../../utils/API";

type AuthorizationOverlayProps = {
    onClose: () => void;
}

type AuthorizationOverlayState = {
    authorized: boolean;
    login: string;
    password: string;
    jwtToken?: string;
};

class AuthorizationOverlay extends Component<AuthorizationOverlayProps, AuthorizationOverlayState> {

    constructor(props: AuthorizationOverlayProps) {
        super(props);
        this.state = {
            authorized: false,
            login: '',
            password: ''
        };
    }

    handleChange = ({target: {name, value}}: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({...this.state, [name]: value});
    }

    handleSubmit = async (e: any) => {
        const tokenResponse = await postLogin({...this.state});
        console.log(tokenResponse)
        this.setState({...this.state, authorized: false});
        if (tokenResponse.data.token) {
            const probeResponse = await getProbeLogin(tokenResponse.data.token);
            if (probeResponse && probeResponse.data) {
                localStorage.setItem(STORED_JWT_TOKEN_KEY, tokenResponse.data.token);
                localStorage.setItem(STORED_JWT_TOKEN_VALIDITY_KEY, "VALID");
            } else {
                localStorage.setItem(STORED_JWT_TOKEN_KEY, "");
                localStorage.setItem(STORED_JWT_TOKEN_VALIDITY_KEY, "INVALID");
            }
            this.setState({...this.state, authorized: probeResponse && probeResponse.data});
        }
        e.preventDefault();
    }

    render() {
        const {authorized, login, password, jwtToken} = this.state;

        return (!authorized &&
            <Dialog
                id="login-dialog"
                aria-labelledby="login-dialog"
                open={!authorized}
            >
                <DialogTitle
                    id="login-dialog-title"
                >
                    Enter login info
                </DialogTitle>
                <DialogContent>

                    {!jwtToken && authorized && <Alert
                        severity="error"
                    >
                        {authorized}
                    </Alert>}
                    <TextField
                        label="Login"
                        name="login"
                        onChange={this.handleChange}
                        value={login}
                        variant="outlined"
                        fullWidth
                    />
                    <TextField
                        label="Password"
                        name="password"
                        type={"password"}
                        onChange={this.handleChange}
                        value={password}
                        variant="outlined"
                        fullWidth
                    />
                    <Button
                        color="primary"
                        type="submit"
                        variant="contained"
                        disabled={!login || !password}
                        onClick={this.handleSubmit}
                    >
                        Login
                    </Button>
                </DialogContent>
            </Dialog>
        );
    }
}

export default AuthorizationOverlay;