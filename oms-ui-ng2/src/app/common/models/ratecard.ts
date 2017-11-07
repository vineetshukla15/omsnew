import { Product } from './product'
import { AdUnit } from './ad.unit'
import { SeasonalDiscount } from './seasonal.discount'
import { Premium } from './premium'

export class RateCard {

    rateCardId: string;
    createdDate: Date;
    modifiedDate: Date;
    basePrice: number;
    sectionsName: string;
    notes: string;
    isActive: number;
    version: number;
    createdBy: string;
    modifiedBy: string;
    isRatecardrounded: number;
    product: Product;
    adUnits: Array<AdUnit>;
    premiums: Array<Premium>;
    seasonalDiscounts: Array<SeasonalDiscount>;

}