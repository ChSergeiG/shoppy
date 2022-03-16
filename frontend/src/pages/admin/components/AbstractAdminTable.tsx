import React from "react";
import {
    Button,
    ButtonGroup,
    CircularProgress,
    MenuItem,
    Select,
    SelectChangeEvent,
    Table,
    TableBody,
    TableCell,
    TableRow
} from "@mui/material";
import {getStatuses} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";
import type {
    IAbstractAdminProps,
    IAbstractAdminState,
    IAdminContent,
    IAdminTableRow
} from "../../../../types/AdminTypes";
import floppyIcon from "../../../img/floppy.svg";
import refreshIcon from "../../../img/refresh.svg";
import binIcon from "../../../img/bin.svg";
import {
    SNACKBAR_EVENT_KEY,
    SNACKBAR_MESSAGE_SEVERITY_KEY,
    SNACKBAR_MESSAGE_VALUE_KEY
} from "../../../components/ShopSnackBar";
import type {IResponseType} from "../../../../types/IResponseType";
import {SnackBarContext} from "../../../snackBarContext";

class AbstractAdminTable<T extends IAdminContent>
    extends React.Component<IAbstractAdminProps<T>, IAbstractAdminState<T>> {

    static contextType = SnackBarContext;
    // @ts-ignore
    context!: React.ContextType<typeof SnackBarContext>

    constructor(props: IAbstractAdminProps<T>) {
        super(props);
        this.state = {
            isLoading: true,
            statuses: [],
            rows: []
        };
    };

    async componentDidMount() {
        const {
            getDataCallback,
            idExtractor,
            keyExtractor,
        } = this.props;

        let dataResponse = await getDataCallback();
        let statuses = await getStatuses();
        let rows: IAdminTableRow<T>[] = dataResponse.data.map(r => {
            let id = idExtractor(r);
            return {
                number: id !== undefined ? id : -1,
                key: keyExtractor(r),
                content: r
            }
        });
        this.setState({
            ...this.state,
            isLoading: false,
            statuses: statuses.data,
            rows: rows
        });
    };

    createPlusRow = () => {
        const {columns, newEntityCreator, keyExtractor} = this.props;
        return (
            <TableRow key="new">
                <TableCell colSpan={columns} align={"center"}>
                    <Button
                        onClick={() => {
                            const newEntity = newEntityCreator();
                            this.setState({
                                ...this.state,
                                rows: [
                                    ...this.state.rows,
                                    {
                                        number: 0xffff,
                                        key: keyExtractor(newEntity),
                                        content: newEntity
                                    }
                                ]
                            });
                        }}
                    >
                        +
                    </Button>
                </TableCell>
            </TableRow>
        );
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
        const {idExtractor, keyExtractor} = this.props;
        const {rows} = this.state;
        return (
            <TableCell align={"center"} key="action-buttons">
                <ButtonGroup>
                    <Button
                        onClick={async () => {
                            const newEntity = await this.saveEntity(data)
                                .catch((r) => {
                                    this.context.setValues?.({message: r.response.data, color: "warning"})
                                });
                            if (!newEntity || !newEntity.data) {
                                return;
                            }

                            const newRows = rows.filter(r => r.content !== data);
                            newRows.push({
                                number: idExtractor(newEntity.data),
                                key: keyExtractor(newEntity.data),
                                content: newEntity.data
                            });
                            this.setState({...this.state, rows: newRows});
                            this.context.setValues?.({message: "success", color: "success"})
                        }}
                    >
                        <img src={floppyIcon} height={16} width={16} alt='save'/>
                    </Button>
                    <Button
                        onClick={async () => {
                            await this.removeEntity(data);
                        }}
                    >
                        <img src={binIcon} height={16} width={16} alt='remove'/>
                    </Button>
                    <Button
                        onClick={async () => {
                            const newEntity = await this.refreshEntity(data);
                            if (newEntity === undefined) {
                                return;
                            }
                            if (newEntity.status === 499) {
                                sessionStorage.setItem(SNACKBAR_MESSAGE_VALUE_KEY, JSON.stringify(newEntity.data));
                            }
                            const newRows = rows.filter(r => r.content !== data);
                            newRows.push({
                                number: idExtractor(newEntity.data),
                                key: keyExtractor(newEntity.data),
                                content: newEntity.data
                            });
                            this.setState({...this.state, rows: newRows});
                            window.dispatchEvent(new Event(SNACKBAR_EVENT_KEY));
                        }}
                    >
                        <img src={refreshIcon} height={16} width={16} alt='refresh'/>
                    </Button>
                </ButtonGroup>
            </TableCell>
        );
    };

    handleSelectorChange = (e: SelectChangeEvent, row: T) => {
        const selectedStatus = (e.target.value as IStatus);
        this.setState((prevState) => {
            let updatedRows = prevState.rows.map((r) => {
                if (r.content === row) {
                    r.content.status = selectedStatus;
                }
                return r;
            });
            return {...prevState, rows: updatedRows.filter(r => r.content && r.content.status !== "REMOVED")};
        });
    }

    async saveEntity(entity: T): Promise<IResponseType<T>> {
        const {createCallback, updateCallback} = this.props;
        if (entity.id === undefined) {
            return await createCallback(entity);
        } else {
            return await updateCallback(entity);
        }
    }

    async removeEntity(entity: T) {
        const {deleteCallback} = this.props;
        if (entity !== undefined) {
            await deleteCallback(entity);
        }
        this.setState({...this.state, rows: this.state.rows.filter(r => r.content !== entity)});
    }

    async refreshEntity(entity: T): Promise<IResponseType<T> | undefined> {
        const {refreshCallback} = this.props;
        if (entity && refreshCallback) {
            return await refreshCallback(entity);
        }
        return undefined;
    }


    render() {
        const {keyExtractor, headerRowBuilder, bodyCellCreator, columns} = this.props;
        const {isLoading} = this.state;
        return (
            isLoading === undefined || isLoading ?
                <CircularProgress/> :
                <Table>
                    {headerRowBuilder()}
                    <TableBody>
                        {
                            this.state.rows
                                .sort((l, r) =>
                                    (l.number !== undefined ? l.number : 0) - (r.number !== undefined ? r.number : 0))
                                .map(r => r.content &&
                                    <TableRow key={keyExtractor(r.content)}>
                                        {
                                            [...Array(columns).keys()].map((value) => !!r.content
                                                ? bodyCellCreator(
                                                    value,
                                                    r.content,
                                                    this.renderStatusSelectorCell,
                                                    this.renderActionsSelectorCell
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

AbstractAdminTable.contextType = SnackBarContext;

export default AbstractAdminTable;
