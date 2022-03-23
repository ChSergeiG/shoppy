import React, {useEffect, useState} from "react";
import {Autocomplete, Input, MenuItem, Select, Table, TableBody, TableHead, TextField} from "@mui/material";
import type {IAccount, IAdminTableProps, IAdminTableState} from "../../../../types/AdminTypes";
import type {IAccountRole} from "../../../../types/IAccountRole";
import {ApplicationContext} from "../../../applicationContext";
import Spinner from "./Spinner";
import {getAccounts} from "../../../utils/API";
import {
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import type {IStatus} from "../../../../types/IStatus";

const AccountsTable: React.FC<IAdminTableProps<IAccount>> = (props) => {

    const context = React.useContext(ApplicationContext);

    const [state, setState] = useState<IAdminTableState<IAccount>>({
        isLoading: true,
        statuses: context.statuses,
        accountRoles: context.accountRoles,
        rows: [],
        sortBy: "",
    });

    setInterval(() => {console.log(state)}, 1000)

    const createHeaderRow = () => commonCreateHeaderRow(
        "header-IAccount",
        [
            {columnNumber: 0, width: "10%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "15%", align: "center", key: "login", value: "Login"},
            {columnNumber: 2, width: "15%", align: "center", key: "password", value: "Password"},
            {columnNumber: 3, width: "30%", key: "group", value: "Group"},
            {columnNumber: 4, width: "10%", key: "status", value: "Status"},
            {columnNumber: 5, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);

    const renderLoginInput = (entity: IAccount) => {
        return (
            <Input
                fullWidth={true}
                defaultValue={entity.login}
                onChange={(e) => {
                    console.log("+++")

                    const index = state.rows.indexOf(entity);
                    const newRows = [...state.rows];
                    newRows[index] = {...state.rows[index], login: e.target.value};
                    setState({
                        ...state,
                        rows: newRows
                    });
                    console.log("+++")

                }}
            />
        );
    };

    const renderPasswordInput = (entity: IAccount) => {
        return (
            <Input
                fullWidth={true}
                defaultValue={entity.password}
                // type={'password'}
                onChange={(e) => {
                    console.log("+++")
                    const index = state.rows.indexOf(entity);
                    const newRows = [...state.rows];
                    newRows[index] = {...state.rows[index], password: e.target.value};
                    setState({
                        ...state,
                        rows: newRows
                    });
                    console.log("+++")
                }}
            />
        );
    };

    const renderGroupInput = (entity: IAccount) => {
        return (
            <Autocomplete
                getOptionLabel={(option: IAccountRole) => option.toUpperCase()}
                onChange={(e, v) => {
                }}
                options={context.accountRoles}
                renderInput={(params) => <TextField {...params} variant="outlined" fullWidth/>}
                value={entity.accountRoles}
                fullWidth
                multiple
            />
        );
    };

    const renderStatusInput = (entity: IAccount) => {
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

    const createBodyRow = (entity: IAccount) => commonCreateBodyRow(
        entity.login,
        [
            {columnNumber: 0, key: "id", content: entity.id},
            {columnNumber: 1, key: "login", content: renderLoginInput(entity)},
            {columnNumber: 2, key: "password", content: renderPasswordInput(entity)},
            {columnNumber: 3, key: "group", content: renderGroupInput(entity)},
            {columnNumber: 4, key: "status", content: renderStatusInput(entity)},
            {columnNumber: 5, key: "actions", content: commonRenderActionsInput<IAccount>(context, entity, props)}
        ]
    );

    useEffect(() => {
        getAccounts(context)
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
                        {state.rows.map(r => createBodyRow(r))}
                        {
                            commonCreatePlusRow<IAccount>(
                                props.columns,
                                {
                                    id: undefined,
                                    login: "",
                                    password: "",
                                    salted: false,
                                    status: "ADDED",
                                    accountRoles: [],
                                },
                                setState
                            )
                        }
                    </TableBody>
                </Table>
            )
    );
}

export default AccountsTable;
