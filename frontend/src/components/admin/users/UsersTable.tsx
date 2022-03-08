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
import {createNewUser, deleteExistingUser, getStatuses, getUsers, updateExistingUser} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";
import type {IAdminTableRow, IAdminTableState, IUser} from "../../../../types/AdminTypes";

class UsersTable extends React.Component<{}, IAdminTableState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            ...this.state,
            isLoading: true,
            statuses: [],
            rows: []
        };
    }

    createRow = (user: IUser, statuses: IStatus[]) => {
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

    createNewRow = (user: IUser) => {
        return this.createRow(user, this.state.statuses)
    }

    handleSelectorChange = (e: SelectChangeEvent, row: IUser) => {
        const selectedStatus = (e.target.value as IStatus);
        console.log("selected: " + selectedStatus)

        this.setState((prevState) => {
            console.log("prev state: ")
            console.log(prevState)
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

    async saveUser(user: IUser) {
        if (user.id === undefined) {
            await createNewUser(user);
        } else {
            await updateExistingUser(user);
        }
    }

    async removeUser(user: IUser) {
        if (user !== undefined) {
            await deleteExistingUser(user);
        }
    }

    async refreshUser(user: IUser) {

    }

    newUser = () => {
        this.setState(prev => {
            const rows = prev.rows;
            let newUser: IUser = {
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
        let userResponse = await getUsers();
        let statuses = await getStatuses();
        let rows: IAdminTableRow[] = userResponse.data.map(r => {
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
                            <TableCell>Name</TableCell>
                            <TableCell>Pass</TableCell>
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

export default UsersTable;