import {Table, TableBody, TableCell, TableRow} from "@mui/material";
import React, {Component} from "react";

type ButtonBarProps = {
    items: JSX.Element[]
}

class ButtonBar extends Component<ButtonBarProps, {}> {

    constructor(props: ButtonBarProps) {
        super(props);
    }

    render() {
        let {items} = this.props;
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

