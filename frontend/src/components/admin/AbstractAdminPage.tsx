import React from "react";
import type {IAdminContent, IAdminTableRow, IAdminTableState} from "../../../types/AdminTypes";
import {getStatuses} from "../../utils/API";
import type {IStatus} from "../../../types/IStatus";
import type {AxiosResponse} from "axios";

type AbstractAdminProps = {
    responseRequest: () => Promise<AxiosResponse<IAdminContent[]>>;
    idExtractor: (content: IAdminContent) => number | undefined;
    keyExtractor: (content: IAdminContent) => string;
    rowCreator: (content: IAdminContent, statuses: IStatus[]) => JSX.Element;
    plusRowCreator: () => JSX.Element;
}

export class AbstractAdminPage extends React.Component<AbstractAdminProps, IAdminTableState> {

    constructor(props: AbstractAdminProps) {
        super(props);
    }

    async componentDidMount() {
        const {
            responseRequest,
            idExtractor, keyExtractor,
            rowCreator, plusRowCreator
        } = this.props;
        let response: AxiosResponse<IAdminContent[]> = await responseRequest();
        let statuses = await getStatuses();
        let rows: IAdminTableRow[] = response.data.map(r => {
            return {
                number: idExtractor(r),
                key: keyExtractor(r),
                content: r,
                renderedContent: rowCreator(r, statuses.data)
            }
        });
        rows.push({
            number: 0xffff,
            key: '+',
            content: undefined,
            renderedContent: plusRowCreator()
        })
        this.setState({
            ...this.state,
            isLoading: false,
            statuses: statuses.data,
            rows: rows
        });
    }

}