import {Button, Table, TableBody, TableCell, TableRow} from "@mui/material";
import React, {Component} from "react";
import {Link} from "react-router-dom";
import {ApplicationContext} from "../applicationContext";
import {LOCAL_STORAGE_JWT_KEY} from "../pages/admin/components/AuthorizationOverlay";

type ButtonBarProps = {
    items: { adminButton: boolean, element: JSX.Element }[];
}

class ButtonBar extends Component<ButtonBarProps, {}> {

    static contextType = ApplicationContext;
    // @ts-ignore
    context!: React.ContextType<typeof ApplicationContext>

    constructor(props: ButtonBarProps) {
        super(props);
    }

    render() {

        let {items} = this.props;
        if (this.context.authorized) {
            items.push(
                {
                    adminButton: true,
                    element: (
                        <Button
                            color='warning'
                            onClick={() => {
                                this.context.setAuthorized?.(false);
                                localStorage.setItem(LOCAL_STORAGE_JWT_KEY, "");
                            }}
                        >
                            <Link to="/">Logout</Link>
                        </Button>
                    )
                }
            )
        }
        return (
            <Table>
                <TableBody>
                    <TableRow>
                        {items.filter(i => !i.adminButton).map(i =>
                            <TableCell
                                style={{border: 'none'}}
                                key={items.indexOf(i)}
                            >
                                {i.element}
                            </TableCell>
                        )}
                        <TableCell style={{width: '100%', border: 'none'}}/>
                        {items.filter(i => i.adminButton).map(i =>
                            <TableCell
                                style={{border: 'none'}}
                                key={items.indexOf(i)}
                            >
                                {i.element}
                            </TableCell>
                        )}
                    </TableRow>
                </TableBody>
            </Table>
        )
    };
}

export default ButtonBar;

