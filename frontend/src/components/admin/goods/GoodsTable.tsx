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
import {createNewGood, deleteExistingGood, getGoods, getStatuses, updateExistingGood} from "../../../utils/API";
import type {IAdminTableRow, IAdminTableState, IGood} from "../../../../types/AdminTypes";
import type {IStatus} from "../../../../types/IStatus";

class GoodsTable extends React.Component<{}, IAdminTableState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            ...this.state,
            isLoading: true,
            statuses: [],
            rows: []
        };
    }

    createRow = (good: IGood, statuses: IStatus[]) => {
        return (
            <TableRow key={good.id}>
                <TableCell>{good.id}</TableCell>
                <TableCell>
                    <Input
                        fullWidth={true}
                        defaultValue={good.name}
                        onChange={(e) => good.name = e.target.value}
                    />
                </TableCell>
                <TableCell>
                    <Input
                        fullWidth={true}
                        defaultValue={good.article}
                        onChange={(e) => {
                            try {
                                good.article = parseInt(e.target.value)
                            } catch (ignore) {
                            }
                        }}
                    />
                </TableCell>
                <TableCell>
                    <Select
                        value={good.status}
                        onChange={(e) => this.handleSelectorChange(e, good)}
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
                            onClick={() => this.saveGood(good)}
                        >
                            <img src={floppyIcon} height={16} width={16} alt='save'/>
                        </Button>
                        <Button
                            onClick={() => this.removeGood(good)}
                        >
                            <img src={binIcon} height={16} width={16} alt='remove'/>
                        </Button>
                        <Button
                            onClick={() => this.refreshGood(good)}
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
                        onClick={() => this.newGood()}
                    >
                        +
                    </Button>
                </TableCell>
            </TableRow>
        );
    }

    createNewRow = (good: IGood) => {
        return this.createRow(good, this.state.statuses)
    }

    handleSelectorChange = (e: SelectChangeEvent, row: IGood) => {
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

    async saveGood(good: IGood) {
        if (good.id === undefined) {
            await createNewGood(good);
        } else {
            await updateExistingGood(good);
        }
    }

    async removeGood(good: IGood) {
        if (good !== undefined) {
            await deleteExistingGood(good);
        }
    }

    async refreshGood(god: IGood) {

    }

    newGood = () => {
        this.setState(prev => {
            const rows = prev.rows;
            let newGood: IGood = {
                id: undefined,
                name: '',
                article: undefined,
                status: 'ADDED'
            };
            rows.push({
                number: 0xfff8,
                key: '',
                content: newGood,
                renderedContent: this.createNewRow(newGood)
            })
            return {...prev, rows};
        })
    }

    async componentDidMount() {
        let goodsResponse = await getGoods();
        let statuses = await getStatuses();
        let rows: IAdminTableRow[] = goodsResponse.data.map(r => {
            return {
                number: (r.id !== undefined ? r.id : -1),
                key: r.id + ' ' + r.article,
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
                            <TableCell>Article</TableCell>
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

export default GoodsTable;
