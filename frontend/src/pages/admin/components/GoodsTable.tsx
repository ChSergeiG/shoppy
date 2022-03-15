import React from "react";
import {Input, TableCell, TableHead, TableRow} from "@mui/material";
import {createNewGood, deleteExistingGood, getGoods, updateExistingGood} from "../../../utils/API";
import type {IGood} from "../../../../types/AdminTypes";
import AbstractAdminTable from "./AbstractAdminTable";

class GoodsTable extends React.Component {

    createHeaderRow = () => {
        return (
            <TableHead>
                <TableRow>
                    <TableCell width={50}>ID</TableCell>
                    <TableCell>Name</TableCell>
                    <TableCell>Article</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell width={200} align={"center"}>Actions</TableCell>
                </TableRow>
            </TableHead>
        );
    }

    createBodyCell = (
        columnNumber: number,
        good: IGood,
        statusSelectorCallback: (good: IGood) => JSX.Element,
        actionsSelectorCallback: (good: IGood) => JSX.Element
    ): JSX.Element => {
        switch (columnNumber) {
            case 0: {
                return (<TableCell>{good.id}</TableCell>)
            }
            case 1: {
                return (
                    <TableCell>
                        <Input
                            fullWidth={true}
                            defaultValue={good.name}
                            onChange={(e) => good.name = e.target.value}
                        />
                    </TableCell>
                );
            }
            case 2: {
                return (
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
                );
            }
            case 3: {
                return (<>{statusSelectorCallback(good)}</>);
            }
            case 4: {
                return (<>{actionsSelectorCallback(good)}</>);
            }
            default: {
                return (<TableCell/>);
            }
        }
    }

    render() {
        return (
            <AbstractAdminTable
                getDataCallback={getGoods}
                idExtractor={(r) => (r.id !== undefined ? r.id : -1)}
                keyExtractor={(r) => (r.id + ' ' + r.article)}
                headerRowBuilder={this.createHeaderRow}
                bodyCellCreator={this.createBodyCell}
                columns={5}
                newEntityCreator={() => {
                    return {
                        id: undefined,
                        name: '',
                        article: undefined,
                        status: 'ADDED'
                    }
                }}
                createCallback={createNewGood}
                updateCallback={updateExistingGood}
                deleteCallback={deleteExistingGood}
            />
        );
    }
}

export default GoodsTable;
