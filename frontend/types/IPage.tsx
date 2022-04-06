export type IPage<T> = {
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