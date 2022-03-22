import React from "react";
import {Autocomplete, Input, TableCell, TableHead, TableRow, TextField} from "@mui/material";
import {
    createNewAccount,
    deleteExistingAccount,
    getAccount,
    getAccounts,
    updateExistingAccount
} from "../../../utils/API";
import type {IAccount} from "../../../../types/AdminTypes";
import AbstractAdminTable from "./AbstractAdminTable";
import type {IAccountRole} from "../../../../types/IAccountRole";

class AccountsTable extends React.Component {

    createHeaderRow = () => {
        return (
            <TableHead>
                <TableRow>
                    <TableCell
                        width="7%"
                        align="center"
                    >
                        ID
                    </TableCell>
                    <TableCell
                        width="15%"
                    >
                        Login
                    </TableCell>
                    <TableCell
                        width="15%"
                    >
                        Password
                    </TableCell>
                    <TableCell
                        width="23%"
                    >
                        Group
                    </TableCell>
                    <TableCell
                        width="7%"
                    >
                        Status
                    </TableCell>
                    <TableCell
                        width="15%"
                        align="center"
                    >
                        Actions
                    </TableCell>
                </TableRow>
            </TableHead>
        );
    }

    createBodyCell = (
        columnNumber: number,
        account: IAccount,
        idCellCallback: (_: IAccount) => JSX.Element,
        statusSelectorCallback: (_: IAccount) => JSX.Element,
        actionsSelectorCallback: (_: IAccount) => JSX.Element,
        accountRoles: IAccountRole[],
        stateUpdater: (_: IAccount, name: string, ...args: any[]) => void
    ): JSX.Element => {
        switch (columnNumber) {
            case 0: {
                return idCellCallback(account);
            }
            case 1: {
                return (
                    <TableCell key="login">
                        <Input
                            fullWidth={true}
                            defaultValue={account.login}
                            onChange={(e) => {
                                stateUpdater(account, "login", e);
                            }}
                        />
                    </TableCell>
                );
            }
            case 2: {
                return (
                    <TableCell key="password">
                        <Input
                            fullWidth={true}
                            defaultValue={account.password}
                            type={'password'}
                            onChange={(e) => {
                                stateUpdater(account, "password", e);
                            }}
                        />
                    </TableCell>
                );
            }
            case 3: {
                return (
                    <TableCell>
                        <Autocomplete
                            getOptionLabel={(option: IAccountRole) => option.toUpperCase()}
                            onChange={(e, v) => stateUpdater(account, "accountRoles", e, v)}
                            options={accountRoles}
                            renderInput={(params) => <TextField {...params} variant="outlined" fullWidth/>}
                            value={account.accountRoles || []}
                            fullWidth
                            multiple
                        />
                    </TableCell>
                );
            }
            case 4: {
                return (<>{statusSelectorCallback(account)}</>);
            }
            case 5: {
                return (<>{actionsSelectorCallback(account)}</>);
            }
            default: {
                return (<TableCell key="default"/>);
            }
        }
    }

    filterAccount = (data: IAccount, filter?: string): boolean => {
        if (!filter || filter.trim() === "") {
            return true;
        }
        return data.login.toLowerCase().includes(filter.toLowerCase());
    };

    render() {
        return (
            <AbstractAdminTable
                getDataCallback={getAccounts}
                headerRowBuilder={this.createHeaderRow}
                bodyCellCreator={this.createBodyCell}
                columns={6}
                newEntityCreator={() => {
                    return {
                        id: undefined,
                        login: '',
                        password: '',
                        salted: false,
                        status: 'ADDED',
                        accountRoles: []
                    };
                }}
                createCallback={createNewAccount}
                updateCallback={updateExistingAccount}
                deleteCallback={deleteExistingAccount}
                refreshCallback={(context, data) => getAccount(context, data.login)}
                filterCallback={this.filterAccount}
            />
        );
    }
}

export default AccountsTable;
