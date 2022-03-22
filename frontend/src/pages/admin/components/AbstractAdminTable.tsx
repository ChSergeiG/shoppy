import React, {useContext, useEffect, useState} from "react";
import {
    Button,
    ButtonGroup,
    MenuItem,
    Select,
    SelectChangeEvent,
    Table,
    TableBody,
    TableCell,
    TableRow
} from "@mui/material";
import {getAccountRoles, getStatuses} from "../../../utils/API";
import type {IAbstractAdminProps, IAbstractAdminState, IAdminContent} from "../../../../types/AdminTypes";
import floppyIcon from "../../../img/floppy.svg";
import refreshIcon from "../../../img/refresh.svg";
import binIcon from "../../../img/bin.svg";
import type {IResponseType} from "../../../../types/IResponseType";
import {ApplicationContext, verifyAuthorization} from "../../../applicationContext";
import Spinner from "./Spinner";

class AbstractAdminTable<T extends IAdminContent>
    extends React.Component<IAbstractAdminProps<T>, IAbstractAdminState<T>> {

    static contextType = ApplicationContext;
    // @ts-ignore
    context!: React.ContextType<typeof ApplicationContext>

    constructor(props: IAbstractAdminProps<T>) {
        super(props);
        this.state = {
            isLoading: true,
            accountRoles: [],
            statuses: [],
            rows: [],
            sortBy: "",
        };
    };


    async componentDidMount() {
        await verifyAuthorization(this.context);
        await this.loadData();
        let accountRoles = await getAccountRoles();
        let statuses = await getStatuses();
        this.setState({
            ...this.state,
            statuses: statuses.data,
            accountRoles: accountRoles.data
        });
    };

    async loadData() {
        const {getDataCallback,} = this.props;
        this.setState({...this.state, isLoading: true});
        let dataResponse = await getDataCallback(this.context)
            .catch((r) => {
                this.context.setSnackBarValues?.({message: new Date() + " " + r.response.data, color: "error"});
            });
        let rows: T[] | undefined = dataResponse?.data;
        this.setState({
            ...this.state,
            isLoading: false,
            rows: rows || []
        });
    }

    createPlusRow = () => {
        const {columns, newEntityCreator} = this.props;
        return (
            <TableRow key="new">
                <TableCell colSpan={columns} align={"center"}>
                    <Button
                        onClick={() => {
                            const newEntity = newEntityCreator();
                            this.setState({
                                ...this.state,
                                rows: [...this.state.rows, newEntity]
                            });
                            console.log(this.state)
                        }}
                    >
                        +
                    </Button>
                </TableCell>
            </TableRow>
        );
    };

    renderSaveButton = (data: T) => {
        const {rows} = this.state;
        return (
            <Button
                onClick={async () => {
                    const newEntity = await this.saveEntity(data)
                        .catch((r) => {
                            this.context.setSnackBarValues?.({message: r.response.data, color: "warning"})
                        });
                    if (!newEntity || !newEntity.data) {
                        return;
                    }

                    const newRows = rows.filter(r => r !== data);
                    newRows.push(newEntity.data);
                    this.setState({...this.state, rows: newRows});
                    this.context.setSnackBarValues?.({message: "success", color: "success"})
                }}
            >
                <img src={floppyIcon} height={16} width={16} alt='save'/>
            </Button>
        );
    };

    renderRemoveButton = (data: T) => {
        return (
            <Button
                onClick={async () => {
                    await this.removeEntity(data)
                        .catch((r) => {
                            this.context.setSnackBarValues?.({
                                message: "Cant remove entity: " + r.response.data,
                                color: "warning"
                            })
                        });
                }}
            >
                <img src={binIcon} height={16} width={16} alt='remove'/>
            </Button>
        );
    };

    renderRefreshButton = (data: T) => {
        const {rows} = this.state;
        return (
            <Button
                onClick={async () => {
                    const newEntity = await this.refreshEntity(data)
                        .catch((r) => {
                            this.context.setSnackBarValues?.({
                                message: "Cant refresh entity: " + r.response.data,
                                color: "warning"
                            })
                        });
                    if (newEntity === undefined) {
                        return;
                    }
                    if (newEntity.status === 499) {
                        this.context.setSnackBarValues?.({
                            message: JSON.stringify(newEntity.data),
                            color: "error"
                        });
                    }
                    if (typeof newEntity.data === "string") {
                        return
                    }
                    const newRows = rows.filter(r => r !== data);
                    newRows.push(newEntity.data);
                    this.setState({...this.state, rows: newRows});
                }}
            >
                <img src={refreshIcon} height={16} width={16} alt='refresh'/>
            </Button>
        );
    };

    renderIdCell = (data: T) => {
        return (<TableCell key={data.id}>{data.id}</TableCell>);
    };

    renderStatusSelectorCell = (data: T) => {
        const {statuses} = this.state;

        return (
            <TableCell key="status-selector">
                <Select
                    value={data.status}
                    onChange={(e) => this.handleSelectorChange(e, data)}
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
        );
    };

    renderActionsSelectorCell = (data: T) => {
        return (
            <TableCell align={"center"} key="action-buttons">
                <ButtonGroup>
                    {this.renderSaveButton(data)}
                    {this.renderRemoveButton(data)}
                    {this.renderRefreshButton(data)}
                </ButtonGroup>
            </TableCell>
        );
    };

    handleSelectorChange = (e: SelectChangeEvent, row: T) => {
        this.stateUpdater(row, "status", e);
    };

    async saveEntity(entity: T): Promise<IResponseType<T>> {
        const {createCallback, updateCallback} = this.props;
        if (entity.id === undefined) {
            return await createCallback(this.context, entity);
        } else {
            return await updateCallback(this.context, entity);
        }
    }

    async removeEntity(entity: T) {
        const {deleteCallback} = this.props;
        if (entity !== undefined) {
            await deleteCallback(this.context, entity);
        }
        this.setState({...this.state, rows: this.state.rows.filter(r => r !== entity)});
    }

    async refreshEntity(entity: T): Promise<IResponseType<T | string> | undefined> {
        const {refreshCallback} = this.props;
        if (entity && refreshCallback) {
            return await refreshCallback(this.context, entity);
        }
        return undefined;
    }

    stateUpdater = (entity: T, name: string, ...args: any[]) => {
        this.setState((prevState) => {
            const newState = {...prevState, rows: prevState.rows.filter(r => r !== entity)};
            const newRow = prevState.rows.filter(r => r === entity)?.[0];
            if (newRow) {
                if (args?.length === 1) {
                    // @ts-ignore
                    newRow[name] = args?.[0].target.value;
                } else if (args?.length === 2) {
                    // @ts-ignore
                    newRow[name] = args?.[1]
                }
            }
            newState.rows.push(newRow);
            return newState;
        });
    };

    render()  {
        const {filterCallback, headerRowBuilder, bodyCellCreator, columns} = this.props;
        const {isLoading, accountRoles, filter} = this.state;
        return (
            isLoading === undefined || isLoading ?
                <Spinner/> :
                <Table>
                    {headerRowBuilder()}
                    <TableBody>
                        {
                            this.state.rows
                                .filter(r => !!r)
                                .filter((r) => filterCallback?.(r, filter))
                                .sort((r1, r2) => (r1 && r1.id ? r1.id : 0xffff) - (r2 && r2.id ? r2.id : 0xffff))
                                .map(r =>
                                    <TableRow>
                                        {
                                            [...Array(columns).keys()].map((value) => !!r
                                                ? bodyCellCreator(
                                                    value,
                                                    r,
                                                    this.renderIdCell,
                                                    this.renderStatusSelectorCell,
                                                    this.renderActionsSelectorCell,
                                                    accountRoles,
                                                    this.stateUpdater
                                                )
                                                : <TableCell/>
                                            )
                                        }
                                    </TableRow>
                                )
                        }
                        {this.createPlusRow()}
                    </TableBody>
                </Table>
        );
    }
}

export default AbstractAdminTable;
