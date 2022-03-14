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
import binIcon from "../../../img/bin.svg";
import refreshIcon from "../../../img/refresh.svg";
import {
    createNewAccount,
    deleteExistingAccount,
    getStatuses,
    getAccounts,
    updateExistingAccount,
    JWT_TOKEN_COOKIE_KEY
} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";
import type {IAdminTableRow, IAdminTableState, IAccount} from "../../../../types/AdminTypes";
import Cookies from "universal-cookie";

class AccountsTable extends React.Component<{}, IAdminTableState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            ...this.state,
            isLoading: true,
            statuses: [],
            rows: []
        };
    }

    createRow = (user: IAccount, statuses: IStatus[]) => {
        return (
            <TableRow key={user.id}>
                <TableCell>{user.id}</TableCell>
                <TableCell>
                    <Input
                        fullWidth={true}
                        defaultValue={user.name}
                        onChange={(e) => user.name = e.target.value}
                    />
                </TableCell>
                <TableCell>
                    <Input
                        fullWidth={true}
                        defaultValue={user.password}
                        type={'password'}
                        onChange={(e) => user.password = e.target.value}
                    />
                </TableCell>
                <TableCell>
                    <Select
                        value={user.status}
                        onChange={(e) => this.handleSelectorChange(e, user)}
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
                            onClick={() => this.saveUser(user)}
                        >
                            <img src={floppyIcon} height={16} width={16} alt='save'/>
                        </Button>
                        <Button
                            onClick={() => this.removeUser(user)}
                        >
                            <img src={binIcon} height={16} width={16} alt='remove'/>
                        </Button>
                        <Button
                            onClick={() => this.refreshUser(user)}
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

    createNewRow = (user: IAccount) => {
        return this.createRow(user, this.state.statuses)
    }

    handleSelectorChange = (e: SelectChangeEvent, row: IAccount) => {
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

    async saveUser(user: IAccount) {
        if (user.id === undefined) {
            await createNewAccount(user);
        } else {
            await updateExistingAccount(user);
        }
    }

    async removeUser(user: IAccount) {
        if (user !== undefined) {
            await deleteExistingAccount(user);
        }
    }

    async refreshUser(user: IAccount) {

    }

    newUser = () => {
        this.setState(prev => {
            const rows = prev.rows;
            let newUser: IAccount = {
                id: undefined,
                name: '',
                password: '',
                status: 'ADDED'
            };
            rows.push({
                number: 0xfff8,
                key: '',
                content: newUser,
                renderedContent: this.createNewRow(newUser)
            })
            return {...prev, rows};
        })
    }

    async componentDidMount() {
        const cookies = new Cookies();
        const token = cookies.get(JWT_TOKEN_COOKIE_KEY);
        let accountResponse = await getAccounts(token);
        let statuses = await getStatuses();
        let rows: IAdminTableRow[] = accountResponse.data.map(r => {
            return {
                number: (r.id !== undefined ? r.id : -1),
                key: r.name,
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
                            <TableCell>Login</TableCell>
                            <TableCell>Password</TableCell>
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

export default AccountsTable;