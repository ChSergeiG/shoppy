import React, {useEffect, useState} from "react";
import {MenuItem, Select, Table, TableBody, TableHead, TextField} from "@mui/material";
import type {IAdminTableProps, IAdminTableState, IGood} from "../../../../types/AdminTypes";
import {SpinnerOverlay} from "../../../components/Spinner";
import {
    checkFilterCondition,
    commonCreateBodyRow,
    commonCreateHeaderRow,
    commonCreatePlusRow,
    commonRenderActionsInput
} from "../../../utils/admin-tables";
import {getGoods} from "../../../utils/API";
import type {IStatus} from "../../../../types/IStatus";
import {useStore} from "effector-react";
import {IStaticsStore, staticsStore} from "../../../store/StaticsStore";
import {adminFilterStore} from "../../../store/AdminFilterStore";
import {ADMIN_GOODS_KEY} from "../admin.page";

const GoodsTable: React.FC<IAdminTableProps<IGood>> = (props) => {

    const contextStore = useStore<IStaticsStore>(staticsStore);

    const adminFilter = useStore(adminFilterStore);

    const [state, setState] = useState<IAdminTableState<IGood>>({
        isLoading: true,
        statuses: contextStore.statuses,
        accountRoles: contextStore.accountRoles,
        rows: [],
        sortBy: "",
    });

    const createHeaderRow = () => commonCreateHeaderRow(
        "header-IGood",
        [
            {columnNumber: 0, width: "5%", align: "center", key: "id", value: "ID"},
            {columnNumber: 1, width: "20%", key: "name", value: "Name"},
            {columnNumber: 2, width: "20%", key: "price", value: "Price"},
            {columnNumber: 3, width: "20%", key: "article", value: "Article"},
            {columnNumber: 4, width: "15%", key: "status", value: "Status"},
            {columnNumber: 5, width: "20%", align: "center", key: "actions", value: "Actions"},
        ]);

    const renderActionsInput = (entity: IGood) => commonRenderActionsInput<IGood>(
        entity,
        {
            save: entity.name !== undefined && entity.name.trim() !== "" && entity.article !== undefined && entity.article.trim() !== "",
            delete: entity.id !== undefined,
            refresh: true
        },
        props,
        setState
    );

    const createBodyRow = (entity: IGood) => commonCreateBodyRow(
        `row-${state.rows.indexOf(entity)}`,
        [
            {columnNumber: 0, key: "id", content: entity.id},
            {columnNumber: 1, key: "name", content: renderNameInput(entity)},
            {columnNumber: 2, key: "price", content: renderPriceInput(entity)},
            {columnNumber: 3, key: "article", content: renderArticleInput(entity)},
            {columnNumber: 4, key: "status", content: renderStatusInput(entity)},
            {columnNumber: 5, key: "actions", align: "center", content: renderActionsInput(entity)},
        ]
    );

    const renderNameInput = (entity: IGood) => {
        return (
            <TextField
                fullWidth={true}
                label="Name"
                required
                value={entity.name}
                onChange={(e) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], name: e.target.value};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    }

    const renderPriceInput = (entity: IGood) => {
        return (
            <TextField
                fullWidth={true}
                label="Price"
                type="number"
                value={entity.price}
                onChange={(e) => {
                    const newValue = parseFloat(e.target.value.replace(",", "."));
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], price: newValue};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    }

    const renderArticleInput = (entity: IGood) => {
        return (
            <TextField
                fullWidth={true}
                label="Article"
                required
                value={entity.article}
                onChange={(e) => {
                    setState(prevState => {
                        const index = prevState.rows.indexOf(entity);
                        const newRows = [...prevState.rows];
                        newRows[index] = {...prevState.rows[index], article: e.target.value};
                        return {...prevState, rows: newRows};
                    });
                }}
            />
        );
    };

    const renderStatusInput = (entity: IGood) => {
        return (
            <Select
                value={entity.status}
                onChange={(e) => {
                    setState({
                        ...state,
                        rows: [
                            ...state.rows.filter(r => r !== entity),
                            {...entity, status: (e.target.value) as IStatus}
                        ]
                    })
                }}
                defaultValue={""}
            >
                {
                    contextStore.statuses.map(item => (
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
        getGoods()
            .then(r => setState({...state, rows: r.data, isLoading: false}))
    }, []);

    return (
        (state.isLoading === undefined || state.isLoading)
            ? (
                <SpinnerOverlay/>
            ) : (
                <Table>
                    <TableHead>
                        {createHeaderRow()}
                    </TableHead>
                    <TableBody>
                        {
                            state.rows
                                .filter(r => checkFilterCondition(ADMIN_GOODS_KEY, adminFilter, r.article))
                                .sort((r1, r2) => (r1 && r1.id ? r1.id : 0xffff) - (r2 && r2.id ? r2.id : 0xffff))
                                .map(r => createBodyRow(r))
                        }
                        {
                            commonCreatePlusRow<IGood>(
                                props.columns,
                                {
                                    id: undefined,
                                    name: "",
                                    price: 0.0,
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
