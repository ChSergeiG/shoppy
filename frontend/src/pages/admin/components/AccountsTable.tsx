import React, {useEffect, useState} from "react";
import {Autocomplete, MenuItem, Select, Table, TableBody, TableHead, TextField} from "@mui/material";
import type {AccountRole, AdminAccountDto, IAdminTableProps, IAdminTableState, Status} from "../../../types";
import {SpinnerOverlay} from "../../../components/Spinner";
import {getAllAccountsInAdminArea} from "../../../utils/API";
import {
    checkFilterCondition,
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import {useStore} from "effector-react";
import {staticsStore} from "../../../store/StaticsStore";
import {adminFilterStore} from "../../../store/AdminFilterStore";
import {ADMIN_ACCOUNTS_KEY} from "../admin.page";

const AccountsTable: React.FC<IAdminTableProps<AdminAccountDto>> = (props) => {

    const contextStore = useStore(staticsStore);

    const adminFilter = useStore(adminFilterStore);

    const [state, setState] = useState<IAdminTableState<AdminAccountDto>>({
        isLoading: true,
        statuses: contextStore.statuses,
        accountRoles: contextStore.accountRoles,
        rows: [],
        sortBy: "",
    });

    const createHeaderRow = () => commonCreateHeaderRow(
        "header-IAccount",
        [
            {columnNumber: 0, width: "5%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "15%", align: "center", key: "login", value: "Login"},
            {columnNumber: 2, width: "15%", align: "center", key: "password", value: "Password"},
            {columnNumber: 3, width: "30%", key: "group", value: "Group"},
            {columnNumber: 4, width: "15%", key: "status", value: "Status"},
            {columnNumber: 5, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);

    const renderLoginInput = (entity: AdminAccountDto) => {
        return (
            <TextField
                fullWidth={true}
                label="Login"
                required
                value={entity.login}
                onChange={(e) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], login: e.target.value};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    };

    const renderPasswordInput = (entity: AdminAccountDto) => {
        return (
            <TextField
                fullWidth={true}
                label="Password"
                required
                value={entity.password}
                type={'password'}
                onChange={(e) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], password: e.target.value};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    };

    const renderGroupInput = (entity: AdminAccountDto) => {
        return (
            <Autocomplete
                getOptionLabel={(option: AccountRole) => option.toUpperCase()}
                onChange={(e, v) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], accountRoles: v};
                        return {...prevState, rows: newRows};
                    });
                }}
                options={contextStore.accountRoles}
                renderInput={(params) => <TextField {...params} variant="outlined" fullWidth/>}
                value={entity.accountRoles}
                fullWidth
                multiple
            />
        );
    };

    const renderStatusInput = (entity: AdminAccountDto) => {
        return (
            <Select
                value={entity.status}
                onChange={(e) => {
                    setState({
                        ...state,
                        rows: [
                            ...state.rows.filter(r => r !== entity),
                            {...entity, status: (e.target.value) as Status}
                        ]
                    })
                }}
                defaultValue={""}
            >
                {
                    contextStore.statuses.map(item => (
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

    const renderActionsInput = (entity: AdminAccountDto) => commonRenderActionsInput<AdminAccountDto>(
        entity,
        {
            save: entity.login !== undefined && entity.login.trim() !== "" && entity.password !== undefined && entity.password.trim() !== "",
            delete: entity.id !== undefined,
            refresh: true
        },
        props,
        setState
    );

    const createBodyRow = (entity: AdminAccountDto) => commonCreateBodyRow(
        `row-${state.rows.indexOf(entity)}`,
        [
            {columnNumber: 0, key: "id", content: entity.id},
            {columnNumber: 1, key: "login", content: renderLoginInput(entity)},
            {columnNumber: 2, key: "password", content: renderPasswordInput(entity)},
            {columnNumber: 3, key: "group", content: renderGroupInput(entity)},
            {columnNumber: 4, key: "status", content: renderStatusInput(entity)},
            {columnNumber: 5, key: "actions", align: "center", content: renderActionsInput(entity)}
        ]
    );

    useEffect(() => {
        getAllAccountsInAdminArea()
            .then(r => setState({...state, rows: r.data, isLoading: false}))
    }, []);

    return (
        (state.isLoading === undefined || state.isLoading)
            ? (
                <SpinnerOverlay/>
            ) : (
                <Table>
                    <TableHead>
                        {createHeaderRow()}
                    </TableHead>
                    <TableBody>
                        {state.rows
                            .filter(r => checkFilterCondition(ADMIN_ACCOUNTS_KEY, adminFilter, r.login))
                            .sort((r1, r2) => (r1.id ? r1.id : 0xffff) - (r2.id ? r2.id : 0xffff))
                            .map(r => createBodyRow(r))}
                        {
                            commonCreatePlusRow<AdminAccountDto>(
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
