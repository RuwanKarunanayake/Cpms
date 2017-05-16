
const enum GenderEnum {
    'Male',
    'Female'

};
import { User } from '../../shared';
export class Userinfo {
    constructor(
        public id?: number,
        public regNo?: string,
        public dob?: any,
        public address?: string,
        public gender?: GenderEnum,
        public telephone?: string,
        public mobile?: string,
        public user?: User,
    ) {
    }
}
