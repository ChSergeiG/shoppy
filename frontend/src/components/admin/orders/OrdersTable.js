import React from "react";
import {
    Button,
    ButtonGroup,
    CircularProgress,
    Input,
    MenuItem,
    Select,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow
} from "@material-ui/core";
import floppyIcon from "../../../img/floppy.svg";
import refreshIcon from "../../../img/refresh.svg";
import API from "../../../utils/API";

class OrdersTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ...this.state,
            isLoading: true,
            statuses: [],
            rows: []
        };
    }

    createRow = (row, statuses) => {
        return (
            <TableRow key={row.id}>
                <TableCell>{row.id}</TableCell>
                <TableCell>
                    <Input
                        fullWidth={true}
                        defaultValue={row.name}
                        onChange={(e) => row.name = e.target.value}
                    />
                </TableCell>
                <TableCell>
                    <Select
                        value={row.status}
                        onChange={(e) => this.handleSelectorChange(e, row)}
                    >
                        {
                            statuses.map(item => (
                                <MenuItem
                                    key={item}
                                    value={item}
                                >
                                    {item}
                                </MenuItem>
                            ))
                        }
                    </Select>
                </TableCell>
                <TableCell align={"center"}>
                    <ButtonGroup>
                        <Button
                            onClick={() => this.saveOrder(row.id, row.name, row.password, row.status)}
                        >
                            <img src={floppyIcon} height={16} width={16} alt='save'/>
                        </Button>
                        <Button
                            onClick={() => this.refreshOrder(row.id)}
                        >
                            <img src={refreshIcon} height={16} width={16} alt='refresh'/>
                        </Button>
                    </ButtonGroup>
                </TableCell>
            </TableRow>
        );
    }

    createPlusRow = () => {
        return (
            <TableRow key="new">
                <TableCell colSpan={5} align={"center"}>
                    <Button
                        onClick={() => this.newUser()}
                    >
                        +
                    </Button>
                </TableCell>
            </TableRow>
        );
    }

    createNewRow = () => {
        return this.createRow({id: '', name: '', password: '', status: 'ADDED'}, this.state.statuses)
    }

    handleSelectorChange = (e, row) => {
        const status = e.target.value;
        this.setState(prev => {
            const rows = prev.rows.map(r => r.id === row.id
                ? {
                    id: r.id,
                    item: {...r.item, status},
                    generatedRow: this.createRow({...r.item, status}, prev.statuses)
                }
                : r
            );
            return {...prev, rows}
        });
    }

    async saveOrder(id, name, password, status) {
        if (id === '') {
            await API.post("/admin/users/add", {
                name: name,
                password: password,
                status: status
            });
        } else {
            await API.post("/admin/users/update", {
                id: id,
                name: name,
                password: password,
                status: status
            });
        }
    }

    async removeUser(id) {
        let item = this.state.rows.find(r => r.id === id).item
        await API.delete("/admin/users/" + item.name);
    }

    async refreshOrder(id) {

    }

    newUser = () => {
        this.setState(prev => {
            const rows = prev.rows;
            rows.push({
                id: 0xfff8,
                item: {},
                generatedRow: this.createNewRow()
            })
            return {...prev, rows};
        })
    }

    async componentDidMount() {
        let userResponse = await API.get("/admin/users/get_all");
        let statuses = await API.get("statuses");
        let rows = userResponse.data.map(r => {
            return {
                id: r.id,
                item: r,
                generatedRow: this.createRow(r, statuses.data)
            };
        });
        rows.push({
            id: 0xffff,
            item: {},
            generatedRow: this.createPlusRow()
        })
        this.setState({
            ...this.state,
            isLoading: false,
            statuses: statuses.data,
            rows: rows
        });
    }

    render() {
        const {isLoading} = this.state;
        return (
            isLoading === undefined || isLoading ?
                <CircularProgress/> :
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell width={50}>ID</TableCell>
                            <TableCell>Info</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell width={200} align={"center"}>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.rows.sort((l, r) => l.id - r.id).map(r => r.generatedRow)}
                    </TableBody>
                </Table>
        );
    }

}

export default OrdersTable;
