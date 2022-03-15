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
import type {IAdminContent} from "../../../../types/AdminTypes";
import floppyIcon from "../../../img/floppy.svg";
import refreshIcon from "../../../img/refresh.svg";
import binIcon from "../../../img/bin.svg";

type AbstractAdminProps<T extends IAdminContent> = {
    getDataCallback: () => Promise<{ data: T[] }>;
    idExtractor: (data: T) => number | undefined;
    keyExtractor: (data: T) => string;
    headerRowBuilder: () => JSX.Element;
    bodyCellCreator: (
        columnNumber: number,
        data: T,
        statusSelectorCallback: (data: T) => JSX.Element,
        actionsSelectorCallback: (data: T) => JSX.Element
    ) => JSX.Element;
    columns: number;
    newEntityCreator: () => T;
    createCallback: (data: T) => Promise<{ data: T }>;
    updateCallback: (data: T) => Promise<{ data: T }>;
    deleteCallback: (data: T) => Promise<{}>;
    refreshCallback?: (data: T) => Promise<{ data: T[] }>;
};

type AbstractAdminState<T extends IAdminContent> = {
    isLoading: boolean;
    statuses: IStatus[];
    rows: AdminTableRow<T>[];
};

/**
 * Represents extended container of entity
 */
type AdminTableRow<T> = {
    // table row number
    number: number | undefined;
    // unique key to identify row
    key: string;
    // which object this row accords
    content: T | undefined;
}

class AbstractAdminTable<T extends IAdminContent>
    extends React.Component<AbstractAdminProps<T>, AbstractAdminState<T>> {

    constructor(props: AbstractAdminProps<T>) {
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
        let rows: AdminTableRow<T>[] = dataResponse.data.map(r => {
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
        const {columns, newEntityCreator, idExtractor, keyExtractor} = this.props;
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
                                        number: idExtractor(newEntity),
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
            <TableCell>
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
            <TableCell align={"center"}>
                <ButtonGroup>
                    <Button
                        onClick={() => this.saveEntity(data)}
                    >
                        <img src={floppyIcon} height={16} width={16} alt='save'/>
                    </Button>
                    <Button
                        onClick={() => this.removeEntity(data)}
                    >
                        <img src={binIcon} height={16} width={16} alt='remove'/>
                    </Button>
                    <Button
                        onClick={() => this.refreshEntity(data)}
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
            return {...prevState, rows: updatedRows};
        });
    }

    async saveEntity(entity: T) {
        const {createCallback, updateCallback} = this.props;
        if (entity.id === undefined) {
            await createCallback(entity);
        } else {
            await updateCallback(entity);
        }
    }

    async removeEntity(entity: T) {
        const {deleteCallback} = this.props;
        if (entity !== undefined) {
            await deleteCallback(entity);
        }
    }

    async refreshEntity(entity: T) {
        const {refreshCallback} = this.props;
        if (entity && refreshCallback) {
            await refreshCallback(entity);
        }
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

export default AbstractAdminTable;
