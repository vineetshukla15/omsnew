import { SpecType } from './spec.type';
import { RateType } from './rate.type';

export class OrderLineItem {
    lineItemId: number;
    product: any;
    flightStartdate: any;
    flightEndDate: any;
    target: any = [];
    proposedCost: number;
    proposedCostView: string;
    specType: SpecType;
    name: string;
    type: string;
    cpms: string;
    avails: string;
    quantity: number;
    tempId: number;
    proposal: any;
    deliveryImpressions: string;
    displayCreatives: string;
    rotateCreatives: string;
    priority: string;
    rateType: RateType;
    cretive:any;
    creativeAsset:any;

    // productId: any;
    // specTypeId:any;
    // rateTypeId:any;
    // creativeAsset:any;

}


export class LineItemOrder {
    orderId: number;
    name: string;
}