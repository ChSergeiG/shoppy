import React from "react";
import {Component} from "react";
import {
    Box,
    Button,
    Dialog,
    FormControl, FormGroup,
    FormHelperText,
    Input,
    InputLabel,
    Modal,
    ModalRoot,
    TextField
} from "@mui/material";
import {getProbeLogin, JWT_TOKEN_COOKIE_KEY, postLogin} from "../../utils/API";
import Cookies from "universal-cookie";

type AuthorizationOverlayState = {
    authorized: boolean;
    login: string;
    password: string;
};

class AuthorizationOverlay extends Component<{}, AuthorizationOverlayState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            authorized: false,
            login: '',
            password: ''
        };
    }

    componentDidMount() {
        const cookie = new Cookies()
        cookie.get("JWT_TOKEN_COOKIE_KEY")
        // async getProbeLogin()
    }

    editFormLoginValue = (e: any) => {
        this.setState({...this.state, login: e.target.value});
    }

    editFormPassValue = (e: any) => {
        this.setState({...this.state, password: e.target.value});
    }

    handleClose = async (e: any) => {
        const response = await postLogin({...this.state});
        if (response.data.token) {
            const cookie = new Cookies();
            cookie.set(JWT_TOKEN_COOKIE_KEY, response.data.token);
            this.setState({...this.state, authorized: true});
        }
        e.preventDefault();
    }

    render() {
        const {authorized, login, password} = this.state;

        return (!authorized &&
            <Dialog
                style={{borderRadius: "8"}}
                open={!authorized}
                onClose={this.handleClose}
            >
                <FormGroup>
                    <TextField
                        id="login-input"
                        type="text"
                        placeholder="Login:"
                        aria-describedby="login-helper-text"
                        onChange={this.editFormLoginValue}
                        value={login}
                    />
                    <FormHelperText id="login-helper-text">Enter login</FormHelperText>
                    <TextField
                        id="password-input"
                        type="password"
                        placeholder="Password:"
                        aria-describedby="password-helper-text"
                        onChange={this.editFormPassValue}
                        value={password}
                    />
                    <FormHelperText id="password-helper-text">Enter password</FormHelperText>
                    <Button
                        type="submit"
                        onClick={this.handleClose}
                    >
                        Login
                    </Button>
                </FormGroup>
            </Dialog>
        );
    }

}

export default AuthorizationOverlay;