import { AudienceTargetValues } from './audience.target.values';
export class AudienceTargetType {

    targetTypeId: number;
    targetId:number;
    name: string;
    status: boolean;
    audienceTargetValues: Array<AudienceTargetValues>;
    tempId: number;
    targetTypeAudienceName: string;
}