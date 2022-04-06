import {Button, ButtonGroup, TableCell, TableRow} from "@mui/material";
import type {IAdminContent, IAdminTableProps, IAdminTableState} from "../../types/AdminTypes";
import React from "react";
import type {TableCellProps} from "@mui/material/TableCell/TableCell";
import floppyIcon from "../img/floppy.svg";
import type {IApplicationContext} from "../../types/IApplicationContextType";
import binIcon from "../img/bin.svg";
import refreshIcon from "../img/refresh.svg";
import type {IResponseType} from "../../types/IResponseType";

export const commonCreateHeaderRow = (
    rowKey: string,
    params: {
        columnNumber: number;
        width?: number | string;
        key: string;
        align?: TableCellProps["align"];
        value: string;
    }[]
): JSX.Element => {
    return (
        <TableRow
            key={rowKey}
        >
            {
                params
                    .sort((p1, p2) => p1.columnNumber - p2.columnNumber)
                    .map(p =>
                        <TableCell width={p.width} key={p.key} align={p.align}>{p.value}</TableCell>
                    )
            }
        </TableRow>
    );
}

export const commonCreateBodyRow = (
    rowKey: string,
    params: {
        columnNumber: number;
        key: string | number;
        align?: TableCellProps["align"];
        content: JSX.Element | string | number | undefined;
    }[]
): JSX.Element => {
    return (
        <TableRow
            key={rowKey}
        >
            {
                params
                    .sort((p1, p2) => p1.columnNumber - p2.columnNumber)
                    .map(p =>
                        <TableCell key={p.key} align={p.align}>{p.content}</TableCell>
                    )
            }
        </TableRow>
    );
};

export const commonCreatePlusRow = <T extends IAdminContent>(
    colspan: number,
    newEntity: T,
    setStateCallback: (value: React.SetStateAction<IAdminTableState<T>>) => void
): JSX.Element => {
    return (
        <TableRow key="new">
            <TableCell colSpan={colspan} align={"center"}>
                <Button
                    onClick={() => {
                        setStateCallback(prevState => {
                            return {
                                ...prevState,
                                rows: [...prevState.rows, newEntity]
                            };
                        });
                    }}
                >
                    +
                </Button>
            </TableCell>
        </TableRow>
    );
};

export const commonRenderActionsInput = <T extends IAdminContent>(
    context: IApplicationContext,
    entity: T,
    isButtonActive: { save: boolean, delete: boolean, refresh: boolean },
    props: IAdminTableProps<T>,
    setStateCallback: (value: React.SetStateAction<IAdminTableState<T>>) => void
) => {
    return (
        <ButtonGroup>
            <Button
                disabled={!isButtonActive.save}
                onClick={async () => {
                    let newEntity: IResponseType<T> | void;
                    if (entity.id) {
                        newEntity = await props.updateCallback(entity)
                            .catch((r) => {
                                context.setSnackBarValues?.({message: r.response.data, color: "warning"})
                            });
                    } else {
                        newEntity = await props.createCallback(entity)
                            .catch((r) => {
                                context.setSnackBarValues?.({message: r.response.data, color: "warning"})
                            });
                    }
                    if (!newEntity || !newEntity.data) {
                        return;
                    }
                    setStateCallback(prevState => {
                        const newRows = [...prevState.rows];
                        const index = newEntity && newEntity.data && prevState.rows.indexOf(entity);
                        if (index && index >= 0 && newEntity && newEntity.data) {
                            newRows[index] = newEntity.data;
                        }
                        return {...prevState, rows: newRows};
                    });
                    context.setSnackBarValues?.({message: "success", color: "success"})
                }}
            >
                <img
                    style={isButtonActive.save ? {} : {opacity: "0.1"}}
                    src={floppyIcon}
                    height={16}
                    width={16}
                    alt="save"
                />
            </Button>
            <Button
                disabled={!isButtonActive.delete}
                onClick={async () => {
                    await props.deleteCallback(entity)
                        .catch((r) => {
                            context.setSnackBarValues?.({
                                message: "Cant remove entity: " + r.response.data,
                                color: "warning"
                            })
                        });
                    setStateCallback(prevState => {
                        const newRows = [...prevState.rows.filter(r => r !== entity)];
                        return {...prevState, rows: newRows};
                    });
                }}
            >
                <img
                    style={isButtonActive.delete ? {} : {opacity: "0.1"}}
                    src={binIcon}
                    height={16}
                    width={16}
                    alt="remove"
                />
            </Button>
            <Button
                disabled={!isButtonActive.refresh}
                onClick={async () => {
                    const newEntity = await props.refreshCallback(entity)
                        .catch((r) => {
                            context.setSnackBarValues?.({
                                message: "Cant refresh entity: " + r.response.data,
                                color: "warning"
                            })
                        });
                    if (newEntity === undefined) {
                        return;
                    }
                    setStateCallback(prevState => {
                        const newRows = [...prevState.rows];
                        const index = newEntity && newEntity.data && prevState.rows.indexOf(entity);
                        if (index && index >= 0 && newEntity && newEntity.data) {
                            newRows[index] = newEntity.data;
                        }
                        return {...prevState, rows: newRows};
                    });
                    if (newEntity.status === 499) {
                        context.setSnackBarValues?.({
                            message: JSON.stringify(newEntity.data),
                            color: "error"
                        });
                    }

                }}
            >
                <img
                    style={isButtonActive.refresh ? {} : {opacity: "0.1"}}
                    src={refreshIcon}
                    height={16}
                    width={16}
                    alt="refresh"
                />
            </Button>
        </ButtonGroup>
    );
};

export const checkFilterCondition = (findFor?: string, ...findIn: (string | undefined)[]): boolean => {
    if (findFor === undefined || findFor.trim().length === 0) {
        return true;
    }
    return findIn?.some(fi => fi?.toLowerCase().includes(findFor.toLowerCase()));
};
