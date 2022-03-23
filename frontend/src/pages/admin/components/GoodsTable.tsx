import React, {useEffect, useState} from "react";
import {Input, MenuItem, Select, Table, TableBody} from "@mui/material";
import type {IAdminTableProps, IAdminTableState, IGood} from "../../../../types/AdminTypes";
import {ApplicationContext} from "../../../applicationContext";
import Spinner from "./Spinner";
import {
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import {getGoods} from "../../../utils/API";

const GoodsTable: React.FC<IAdminTableProps<IGood>> = (props) => {

    const context = React.useContext(ApplicationContext);

    const [state, setState] = useState<IAdminTableState<IGood>>({
        isLoading: true,
        statuses: context.statuses,
        accountRoles: context.accountRoles,
        rows: [],
        sortBy: "",
    });

    const createHeaderRow = () => commonCreateHeaderRow(
        "header-IGood",
        [
            {columnNumber: 0, width: "10%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "30%", key: "name", value: "Name"},
            {columnNumber: 2, width: "30%", key: "article", value: "Article"},
            {columnNumber: 3, width: "10%", key: "status", value: "Status"},
            {columnNumber: 4, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);


    const createBodyRow = (entity: IGood) => commonCreateBodyRow(
        entity.name.replace(/[\s]+/, "_"),
        [
            {columnNumber: 0, key: "id", content: entity.id},
            {columnNumber: 1, key: "name", content: renderNameInput(entity)},
            {columnNumber: 2, key: "article", content: renderArticleInput(entity)},
            {columnNumber: 3, key: "status", content: renderStatusInput(entity)},
            {columnNumber: 4, key: "actions", content: commonRenderActionsInput<IGood>(context, entity, props)},
        ]
    );

    const renderNameInput = (entity: IGood) => {
        return (
            <Input
                fullWidth={true}
                defaultValue={entity.name}
                onChange={(e) => {
                }}
            />
        );
    }

    const renderArticleInput = (entity: IGood) => {
        return (
            <Input
                fullWidth={true}
                defaultValue={entity.article}
                onChange={(e) => {
                }}
            />
        );
    };

    const renderStatusInput = (entity: IGood) => {
        return (
            <Select
                value={entity.status}
                onChange={(e) => {
                }}
                defaultValue={""}
            >
                {
                    context.statuses.map(item => (
                        <MenuItem
                            key={item}
                            value={item}
                        >
                            {item}
                        </MenuItem>
                    ))
                }
            </Select>
        );
    };

    useEffect(() => {
        getGoods(context)
            .then(r => setState({...state, rows: r.data, isLoading: false}))
    }, []);

    return (
        (state.isLoading === undefined || state.isLoading)
            ? (
                <Spinner/>
            ) : (
                <Table>
                    {createHeaderRow()}
                    <TableBody>
                        {
                            state.rows
                                // .filter((r) => props.filterCallback?.(r) || r.id === undefined)
                                .sort((r1, r2) => (r1 && r1.id ? r1.id : 0xffff) - (r2 && r2.id ? r2.id : 0xffff))
                                .map(r => createBodyRow(r))
                        }
                        {
                            commonCreatePlusRow<IGood>(
                                props.columns,
                                {
                                    id: undefined,
                                    name: "",
                                    article: "",
                                    status: "ADDED",
                                },
                                setState
                            )
                        }
                    </TableBody>
                </Table>
            )
    );

}

export default GoodsTable;
