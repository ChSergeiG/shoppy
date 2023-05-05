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

import type { Status } from './Status';
// import { HttpFile } from '../http/http';

export class AdminGoodDto {
    'name'?: string;
    'article'?: string;
    'status'?: Status;
    'price'?: number;
    'id'?: number;

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{name: string, baseName: string, type: string, format: string}> = [
        {
            "name": "name",
            "baseName": "name",
            "type": "string",
            "format": ""
        },
        {
            "name": "article",
            "baseName": "article",
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
            "name": "price",
            "baseName": "price",
            "type": "number",
            "format": ""
        },
        {
            "name": "id",
            "baseName": "id",
            "type": "number",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return AdminGoodDto.attributeTypeMap;
    }

    public constructor() {
    }
}
