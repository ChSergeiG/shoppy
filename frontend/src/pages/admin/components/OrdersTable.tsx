import React from "react";
import {Input, TableCell, TableHead, TableRow} from "@mui/material";
import {createNewOrder, deleteExistingOrder, getOrder, getOrders, updateExistingOrder} from "../../../utils/API";
import type {IOrder} from "../../../../types/AdminTypes";
import AbstractAdminTable from "./AbstractAdminTable";
import type {IAccountRole} from "../../../../types/IAccountRole";

class OrdersTable extends React.Component {

    createHeaderRow = () => {
        return (
            <TableHead>
                <TableRow>
                    <TableCell width={50}>ID</TableCell>
                    <TableCell>Info</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell width={200} align={"center"}>Actions</TableCell>
                </TableRow>
            </TableHead>
        );
    }

    createBodyCell = (
        columnNumber: number,
        order: IOrder,
        idCellCallback: (_: IOrder) => JSX.Element,
        statusSelectorCallback: (order: IOrder) => JSX.Element,
        actionsSelectorCallback: (order: IOrder) => JSX.Element,
        accountRoles: IAccountRole[],
        stateUpdater: (entity: IOrder, name: string, ...args: any[]) => void
    ): JSX.Element => {
        switch (columnNumber) {
            case 0: {
                return idCellCallback(order);
            }
            case 1: {
                return (
                    <TableCell key="info">
                        <Input
                            fullWidth={true}
                            defaultValue={order.info}
                            onChange={(e) => {
                                stateUpdater(order, "info", e);
                            }}
                        />
                    </TableCell>
                );
            }
            case 2: {
                return (<>{statusSelectorCallback(order)}</>);
            }
            case 3: {
                return (<>{actionsSelectorCallback(order)}</>);
            }
            default: {
                return (<TableCell key="default"/>);
            }
        }
    }

    render() {
        return (
            <AbstractAdminTable
                getDataCallback={getOrders}
                headerRowBuilder={this.createHeaderRow}
                bodyCellCreator={this.createBodyCell}
                columns={5}
                newEntityCreator={() => {
                    return {
                        id: undefined,
                        info: '',
                        status: 'ADDED'
                    }
                }}
                createCallback={createNewOrder}
                updateCallback={updateExistingOrder}
                deleteCallback={deleteExistingOrder}
                refreshCallback={(context, data) => getOrder(context, data.id)}

            />
        );
    }
}

export default OrdersTable;
