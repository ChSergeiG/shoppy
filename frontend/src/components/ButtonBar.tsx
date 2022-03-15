import {Button, Table, TableBody, TableCell, TableRow} from "@mui/material";
import React, {Component} from "react";
import {Link} from "react-router-dom";
import {STORED_JWT_TOKEN_KEY} from "../utils/API";

type ButtonBarProps = {
    items: JSX.Element[];
    authorized: boolean;
}

class ButtonBar extends Component<ButtonBarProps, {}> {

    constructor(props: ButtonBarProps) {
        super(props);
        this.state = {authorized: false};
    }

    render() {
        let {items, authorized} = this.props;
        if (authorized) {
            items.push(
                <Button
                    color='warning'
                    onClick={() => localStorage.setItem(STORED_JWT_TOKEN_KEY, "")}
                >
                    <Link to="/">Logout</Link>
                </Button>
            );
        }
        return (
            <Table style={{width: '10%'}}>
                <TableBody>
                    <TableRow>
                        {items.map(i =>
                            <TableCell style={{border: 'none'}} key={items.indexOf(i)}>
                                {i}
                            </TableCell>
                        )}
                    </TableRow>
                </TableBody>
            </Table>
        )
    };
}

export default ButtonBar;

