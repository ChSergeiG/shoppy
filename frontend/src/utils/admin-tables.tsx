import {Button, ButtonGroup, TableCell, TableRow} from "@mui/material";
import type {IAdminContent, IAdminTableProps, IAdminTableState} from "../../types/AdminTypes";
import React from "react";
import type {TableCellProps} from "@mui/material/TableCell/TableCell";
import floppyIcon from "../img/floppy.svg";
import type {IApplicationContext} from "../../types/IApplicationContextType";
import binIcon from "../img/bin.svg";
import refreshIcon from "../img/refresh.svg";

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
    props: IAdminTableProps<T>
) => {
    return (
        <ButtonGroup>
            <Button
                onClick={async () => {
                    const newEntity = await props.createCallback(context, entity)
                        .catch((r) => {
                            context.setSnackBarValues?.({message: r.response.data, color: "warning"})
                        });
                    if (!newEntity || !newEntity.data) {
                        return;
                    }
                    context.setSnackBarValues?.({message: "success", color: "success"})
                }}
            >
                <img src={floppyIcon} height={16} width={16} alt='save'/>
            </Button>
            <Button
                onClick={async () => {
                    await props.deleteCallback(context, entity)
                        .catch((r) => {
                            context.setSnackBarValues?.({
                                message: "Cant remove entity: " + r.response.data,
                                color: "warning"
                            })
                        });
                }}
            >
                <img src={binIcon} height={16} width={16} alt='remove'/>
            </Button>
            <Button
                onClick={async () => {
                    const newEntity = await props.refreshCallback(context, entity)
                        .catch((r) => {
                            context.setSnackBarValues?.({
                                message: "Cant refresh entity: " + r.response.data,
                                color: "warning"
                            })
                        });
                    if (newEntity === undefined) {
                        return;
                    }
                    if (newEntity.status === 499) {
                        context.setSnackBarValues?.({
                            message: JSON.stringify(newEntity.data),
                            color: "error"
                        });
                    }
                }}
            >
                <img src={refreshIcon} height={16} width={16} alt='refresh'/>
            </Button>
        </ButtonGroup>
    );
};

