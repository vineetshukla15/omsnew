

export class Product {
    productId: number;
    name: string;
    description: string;
    status: boolean;
    creatives: Array<any> = [];
    productType: any;
    rateType: any;
    adUnits: Array<any> = [];
    deliveryImpressions: string;
    displayCreatives: string;
    rotateCreatives: string;
    target: any = [];
    priority: string;
}