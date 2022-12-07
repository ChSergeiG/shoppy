import type {AccountRole} from './AccountRole'
import {AdminAccountDto} from './AdminAccountDto'
import {AdminCountedGoodDto} from './AdminCountedGoodDto'
import {AdminGoodDto} from './AdminGoodDto'
import {AdminOrderAccountsDto} from './AdminOrderAccountsDto'
import {AdminOrderDto} from './AdminOrderDto'
import {AdminOrderGoodsDto} from './AdminOrderGoodsDto'
import {CommonGoodDto} from './CommonGoodDto'
import {CommonOrderDto} from './CommonOrderDto'
import {ExtendedOrderDto} from './ExtendedOrderDto'
import {JwtRequestDto} from './JwtRequestDto'
import {JwtResponseDto} from './JwtResponseDto'
import {OrderEntry} from './OrderEntry'
import type {Status} from './Status'

import type {AxiosResponse} from "axios";

type IAdminContent = AdminAccountDto | AdminGoodDto | AdminOrderDto;

type IAdminTableProps<T extends IAdminContent> = {
    getDataCallback: () => Promise<AxiosResponse<T[]>>;
    columns: number;
    newEntityCreator?: () => T;
    createCallback: (entity: T) => Promise<AxiosResponse<T>>;
    updateCallback: (entity: T) => Promise<AxiosResponse<T>>;
    deleteCallback: (entity: T) => Promise<AxiosResponse<{}>>;
    refreshCallback: (entity: T) => Promise<AxiosResponse<T> | undefined>;
};

type IAdminTableState<T extends IAdminContent> = {
    isLoading: boolean;
    statuses: Status[];
    accountRoles: AccountRole[];
    rows: T[];
    sortBy: string;
    sortDirection?: boolean;
};

type IPage<T> = {
    content: T[];
    pageable: {
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        };
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: number;
        unpaged: boolean;
    };
    last: boolean;
    totalPages: number;
    totalElements: number;
    number: number;
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    first: boolean;
    size: number;
    numberOfElements: number;
    empty: boolean;
};

export {
    AccountRole,
    AdminAccountDto,
    AdminCountedGoodDto,
    AdminGoodDto,
    AdminOrderAccountsDto,
    AdminOrderDto,
    AdminOrderGoodsDto,
    CommonGoodDto,
    CommonOrderDto,
    ExtendedOrderDto,
    type IAdminContent,
    type IAdminTableProps,
    type IAdminTableState,
    type IPage,
    JwtRequestDto,
    JwtResponseDto,
    OrderEntry,
    Status
};