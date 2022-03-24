import {Button, Table, TableBody, TableCell, TableRow} from "@mui/material";
import React from "react";
import {ApplicationContext} from "../applicationContext";
import {LOCAL_STORAGE_JWT_KEY} from "../pages/admin/components/AuthorizationOverlay";
import {Link} from "react-router-dom";

type ButtonBarProps = {
    items: { adminButton: boolean, element: JSX.Element }[];
}

const ButtonBar: React.FC<ButtonBarProps> = (props) => {

    const context = React.useContext(ApplicationContext);

    return (
        <Table>
            <TableBody>
                <TableRow>
                    {props.items.filter(i => !i.adminButton).map(i =>
                        <TableCell
                            style={{border: 'none'}}
                            key={props.items.indexOf(i)}
                        >
                            {i.element}
                        </TableCell>
                    )}
                    <TableCell
                        style={{width: "100%", border: "none"}}
                        key="divider"

                    />
                    {props.items.filter(i => i.adminButton).map(i =>
                        <TableCell
                            style={{border: 'none'}}
                            key={props.items.indexOf(i)}
                        >
                            {i.element}
                        </TableCell>
                    )}
                    {context.authorized &&
                    <TableCell
                        key="logout"
                        style={{border: "none"}}
                    >
                        <Link to="/">
                            <Button
                                color="warning"
                                onClick={() => {
                                    context.setAuthorized?.(false);
                                    context.setToken?.("");
                                    localStorage.setItem(LOCAL_STORAGE_JWT_KEY, "");
                                }}
                            >
                                Logout
                            </Button>
                        </Link>
                    </TableCell>}
                </TableRow>
            </TableBody>
        </Table>
    );
}

export default ButtonBar;

