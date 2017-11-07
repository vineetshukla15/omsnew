import { IMultiSelectOption } from "angular-2-dropdown-multiselect";

export class Premium {
    premiumId: number;
    premiumPercentage: number;
    targetTypeId: number;
    status: any;
    tempId: number;
    targetTypeName: string;
    targetTypeOptions: IMultiSelectOption[];
    targetTypeOptionsModel: number[];
}