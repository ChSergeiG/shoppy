import React, {useEffect, useState} from "react";
import {MenuItem, Select, Table, TableBody, TableHead, TextField} from "@mui/material";
import type {IAdminTableProps, IAdminTableState, IOrder} from "../../../../types/AdminTypes";
import {ApplicationContext} from "../../../applicationContext";
import Spinner from "./Spinner";
import {
    checkFilterCondition,
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import {getOrders} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";

const OrdersTable: React.FC<IAdminTableProps<IOrder>> = (props) => {

    const context = React.useContext(ApplicationContext);

    const [state, setState] = useState<IAdminTableState<IOrder>>({
        isLoading: true,
        statuses: context.statuses,
        accountRoles: context.accountRoles,
        rows: [],
        sortBy: "",
    });

    const createHeaderRow = () => commonCreateHeaderRow(
        "header-IOrder",
        [
            {columnNumber: 0, width: "10%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "60%", key: "info", value: "Info"},
            {columnNumber: 2, width: "10%", key: "status", value: "Status"},
            {columnNumber: 3, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);


    const renderActionsInput = (entity: IOrder) => commonRenderActionsInput<IOrder>(
        context,
        entity,
        {
            save: entity.info !== undefined && entity.info.trim() !== "",
            delete: entity.id !== undefined,
            refresh: true
        },
        props,
        setState
    );


    const createBodyRow = (entity: IOrder) => commonCreateBodyRow(
        `row-${state.rows.indexOf(entity)}`,
        [
            {columnNumber: 0, key: "id", content: entity.id},
            {columnNumber: 1, key: "info", content: renderInfoInput(entity)},
            {columnNumber: 2, key: "status", content: renderStatusInput(entity)},
            {columnNumber: 3, key: "actions", align: "center", content: renderActionsInput(entity)},
        ]
    );

    const renderInfoInput = (entity: IOrder) => {
        return (
            <TextField
                fullWidth={true}
                label="Information"
                required
                value={entity.info}
                onChange={(e) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], info: e.target.value};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    }

    const renderStatusInput = (entity: IOrder) => {
        return (
            <Select
                value={entity.status}
                onChange={(e) => {
                    setState({
                        ...state,
                        rows: [
                            ...state.rows.filter(r => r !== entity),
                            {...entity, status: (e.target.value) as IStatus}
                        ]
                    })
                }}
                defaultValue={""}
            >
                {
                    context.statuses.map(item => (
                        <MenuItem
                            key={item}
                            value={item}
                        >
                            {item}
                        </MenuItem>
                    ))
                }
            </Select>
        );
    };

    useEffect(() => {
        getOrders(context)
            .then(r => setState({...state, rows: r.data, isLoading: false}))
    }, []);

    return (
        (state.isLoading === undefined || state.isLoading)
            ? (
                <Spinner/>
            ) : (
                <Table>
                    <TableHead>
                        {createHeaderRow()}
                    </TableHead>
                    <TableBody>
                        {
                            state.rows
                                .filter(r => checkFilterCondition(context.adminFilter, r.info))
                                .sort((r1, r2) => (r1 && r1.id ? r1.id : 0xffff) - (r2 && r2.id ? r2.id : 0xffff))
                                .map(r => createBodyRow(r as IOrder))
                        }
                        {
                            commonCreatePlusRow<IOrder>(
                                props.columns,
                                {
                                    id: undefined,
                                    info: "",
                                    status: "ADDED",
                                },
                                setState
                            )
                        }
                    </TableBody>
                </Table>
            )
    );

}

export default OrdersTable;
