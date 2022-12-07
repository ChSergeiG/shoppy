/**
 * Shoppy
 * Common spec
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import type { OrderEntry } from './OrderEntry';
import type { Status } from './Status';
// import { HttpFile } from '../http/http';

export class ExtendedOrderDto {
    'id'?: number;
    'info'?: string;
    'status'?: Status;
    'guid'?: string;
    'entries'?: Array<OrderEntry>;

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{name: string, baseName: string, type: string, format: string}> = [
        {
            "name": "id",
            "baseName": "id",
            "type": "number",
            "format": ""
        },
        {
            "name": "info",
            "baseName": "info",
            "type": "string",
            "format": ""
        },
        {
            "name": "status",
            "baseName": "status",
            "type": "Status",
            "format": ""
        },
        {
            "name": "guid",
            "baseName": "guid",
            "type": "string",
            "format": ""
        },
        {
            "name": "entries",
            "baseName": "entries",
            "type": "Array<OrderEntry>",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return ExtendedOrderDto.attributeTypeMap;
    }

    public constructor() {
    }
}

