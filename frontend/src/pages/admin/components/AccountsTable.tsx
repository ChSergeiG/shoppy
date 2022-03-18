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
                    <TableCell width={50}>ID</TableCell>
                    <TableCell>Login</TableCell>
                    <TableCell>Password</TableCell>
                    <TableCell>Group</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell width={200} align={"center"}>Actions</TableCell>
                </TableRow>
            </TableHead>
        );
    }

    createBodyCell = (
        columnNumber: number,
        account: IAccount,
        statusSelectorCallback: (account: IAccount) => JSX.Element,
        actionsSelectorCallback: (account: IAccount) => JSX.Element,
        accountRoles: IAccountRole[]
    ): JSX.Element => {
        switch (columnNumber) {
            case 0: {
                return (<TableCell key="id">{account.id}</TableCell>)
            }
            case 1: {
                return (
                    <TableCell key="login">
                        <Input
                            fullWidth={true}
                            defaultValue={account.login}
                            onChange={(e) => {}}
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
                            onChange={(e) => {}}
                        />
                    </TableCell>
                );
            }
            case 3: {
                return (
                    <TableCell>
                        <Autocomplete
                            getOptionLabel={(option: IAccountRole) => option.toUpperCase()}
                            onChange={(e, v) => {
                            }}
                            options={accountRoles || []}
                            renderInput={(params) => <TextField {...params} variant="outlined" fullWidth/>}
                            value={account.roles}
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

    render() {
        return (
            <AbstractAdminTable
                getDataCallback={getAccounts}
                idExtractor={(r) => (r.id !== undefined ? r.id : -1)}
                keyExtractor={(r) => r.login}
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
                        roles: []
                    }
                }}
                createCallback={createNewAccount}
                updateCallback={updateExistingAccount}
                deleteCallback={deleteExistingAccount}
                refreshCallback={(context, data) => getAccount(context, data.login)}
            />
        );
    }
}

export default AccountsTable;