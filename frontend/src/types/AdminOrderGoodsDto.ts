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

import type { AdminCountedGoodDto } from './AdminCountedGoodDto';
// import { HttpFile } from '../http/http';

export class AdminOrderGoodsDto {
    'orderId'?: number;
    'goods'?: Array<AdminCountedGoodDto>;

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{name: string, baseName: string, type: string, format: string}> = [
        {
            "name": "orderId",
            "baseName": "orderId",
            "type": "number",
            "format": ""
        },
        {
            "name": "goods",
            "baseName": "goods",
            "type": "Array<AdminCountedGoodDto>",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return AdminOrderGoodsDto.attributeTypeMap;
    }

    public constructor() {
    }
}

