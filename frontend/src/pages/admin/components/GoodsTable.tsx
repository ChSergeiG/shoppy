import React from "react";
import {Input, TableCell, TableHead, TableRow} from "@mui/material";
import {createNewGood, deleteExistingGood, getGood, getGoods, updateExistingGood} from "../../../utils/API";
import type {IGood} from "../../../../types/AdminTypes";
import AbstractAdminTable from "./AbstractAdminTable";
import type {IAccountRole} from "../../../../types/IAccountRole";

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
        idCellCallback: (_: IGood) => JSX.Element,
        statusSelectorCallback: (_: IGood) => JSX.Element,
        actionsSelectorCallback: (_: IGood) => JSX.Element,
        accountRoles: IAccountRole[],
        stateUpdater: (_: IGood, name: string, ...args: any[]) => void
    ): JSX.Element => {
        switch (columnNumber) {
            case 0: {
                return idCellCallback(good);
            }
            case 1: {
                return (
                    <TableCell key="name">
                        <Input
                            fullWidth={true}
                            defaultValue={good.name}
                            onChange={(e) => {
                                stateUpdater(good, "name", e);
                            }}
                        />
                    </TableCell>
                );
            }
            case 2: {
                return (
                    <TableCell key="article">
                        <Input
                            fullWidth={true}
                            defaultValue={good.article}
                            onChange={(e) => {
                                stateUpdater(good, "article", e);
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
                return (<TableCell key="default"/>);
            }
        }
    }

    render() {
        return (
            <AbstractAdminTable
                getDataCallback={getGoods}
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
                refreshCallback={(context, data) => getGood(context, data.id)}
            />
        );
    }
}

export default GoodsTable;
