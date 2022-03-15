import React from "react";
import {Input, TableCell, TableHead, TableRow} from "@mui/material";
import {createNewAccount, deleteExistingAccount, getAccounts, updateExistingAccount} from "../../../utils/API";
import type {IAccount} from "../../../../types/AdminTypes";
import AbstractAdminTable from "./AbstractAdminTable";

class AccountsTable extends React.Component {

    createHeaderRow = () => {
        return (
            <TableHead>
                <TableRow>
                    <TableCell width={50}>ID</TableCell>
                    <TableCell>Login</TableCell>
                    <TableCell>Password</TableCell>
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
        actionsSelectorCallback: (account: IAccount) => JSX.Element
    ): JSX.Element => {
        switch (columnNumber) {
            case 0: {
                return (<TableCell>{account.id}</TableCell>)
            }
            case 1: {
                return (
                    <TableCell>
                        <Input
                            fullWidth={true}
                            defaultValue={account.login}
                            onChange={(e) => account.login = e.target.value}
                        />
                    </TableCell>
                );
            }
            case 2: {
                return (
                    <TableCell>
                        <Input
                            fullWidth={true}
                            defaultValue={account.password}
                            type={'password'}
                            onChange={(e) => account.password = e.target.value}
                        />
                    </TableCell>
                );
            }
            case 3: {
                return (<>{statusSelectorCallback(account)}</>);
            }
            case 4: {
                return (<>{actionsSelectorCallback(account)}</>);
            }
            default: {
                return (<TableCell/>);
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
                columns={5}
                newEntityCreator={() => {
                    return {
                        id: undefined,
                        login: '',
                        password: '',
                        salted: false,
                        status: 'ADDED'
                    }
                }}
                createCallback={createNewAccount}
                updateCallback={updateExistingAccount}
                deleteCallback={deleteExistingAccount}
            />
        );
    }
}

export default AccountsTable;