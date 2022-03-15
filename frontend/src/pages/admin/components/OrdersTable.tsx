import React from "react";
import {
    Button,
    ButtonGroup,
    CircularProgress,
    Input,
    MenuItem,
    Select,
    SelectChangeEvent,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow
} from "@mui/material";
import floppyIcon from "../../../img/floppy.svg";
import refreshIcon from "../../../img/refresh.svg";
import {createNewOrder, deleteExistingOrder, getOrders, getStatuses, updateExistingOrder} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";
import type {IAdminTableRow, IAdminTableState, IOrder} from "../../../../types/AdminTypes";

type OrdersTableProps = {};

class OrdersTable extends React.Component<OrdersTableProps, IAdminTableState> {

    constructor(props: OrdersTableProps) {
        super(props);
        this.state = {
            ...this.state,
            isLoading: true,
            statuses: [],
            rows: []
        };
    }

    createRow = (order: IOrder, statuses: IStatus[]) => {
        return (
            <TableRow key={order.id}>
                <TableCell>{order.id}</TableCell>
                <TableCell>
                    <Input
                        fullWidth={true}
                        defaultValue={order.info}
                        onChange={(e) => order.info = e.target.value}
                    />
                </TableCell>
                <TableCell>
                    <Select
                        value={order.status}
                        onChange={(e) => this.handleSelectorChange(e, order)}
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
                            onClick={() => this.saveOrder(order)}
                        >
                            <img src={floppyIcon} height={16} width={16} alt='save'/>
                        </Button>
                        <Button
                            onClick={() => this.refreshOrder(order)}
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
                        onClick={() => this.newOrder()}
                    >
                        +
                    </Button>
                </TableCell>
            </TableRow>
        );
    }

    createNewRow = (order: IOrder) => {
        return this.createRow(order, this.state.statuses)
    }

    handleSelectorChange = (e: SelectChangeEvent, row: IOrder) => {
        const selectedStatus = (e.target.value as IStatus);

        this.setState((prevState) => {
            let updatedRows = prevState.rows.map((r) => {
                if (r.content === row) {
                    r.content.status = selectedStatus;
                    r.renderedContent = this.createNewRow(r.content);
                }
                return r;
            });
            return {...prevState, rows: updatedRows};
        });
    }

    async saveOrder(order: IOrder) {
        if (order === undefined || order.id === undefined) {
            await createNewOrder(order);
        } else {
            await updateExistingOrder(order);
        }
    }

    async removeOrder(order: IOrder) {
        if (order !== undefined && order.id !== undefined) {
            await deleteExistingOrder(order);
        }
    }

    async refreshOrder(order: IOrder) {

    }

    newOrder = () => {
        this.setState(prev => {
            const rows = prev.rows;
            let newOrder: IOrder = {
                id: undefined,
                info: '',
                status: 'ADDED'
            };
            rows.push({
                number: 0xfff8,
                key: '',
                content: newOrder,
                renderedContent: this.createNewRow(newOrder)
            })
            return {...prev, rows};
        })
    }

    async componentDidMount() {
        let userResponse = await getOrders();
        let statuses = await getStatuses();
        let rows: IAdminTableRow[] = userResponse.data.map(r => {
            return {
                number: (r.id !== undefined ? r.id : -1),
                key: r.id + r.info,
                content: r,
                renderedContent: this.createRow(r, statuses.data)
            }
        });
        rows.push({
            number: 0xffff,
            key: '+',
            content: undefined,
            renderedContent: this.createPlusRow()
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
                        {
                            this.state.rows
                                .sort((l, r) =>
                                    (l.number !== undefined ? l.number : 0) - (r.number !== undefined ? r.number : 0))
                                .map(r => r.renderedContent)
                        }
                    </TableBody>
                </Table>
        );
    }

}

export default OrdersTable;
