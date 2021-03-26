import {Table, TableBody, TableCell, TableRow} from "@material-ui/core";

const ButtonBar = (items) => {
    return (
        <Table style={{width: '10%'}}>
            <TableBody>
                <TableRow>
                    {items.map(i => <TableCell style={{border: 'none'}}>{i}</TableCell>)}
                </TableRow>
            </TableBody>
        </Table>
    );
};

export default ButtonBar;


