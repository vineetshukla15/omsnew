import { Customer } from './customer';

export class Company {
    companyId: number;
    type: string;
    name: string;
    customer: Customer;
    companyStatus: any;
}