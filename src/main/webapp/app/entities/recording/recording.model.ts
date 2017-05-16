import { User } from '../../shared';
import { Clinic } from '../clinic';
import { Investigation } from '../investigation';
export class Recording {
    constructor(
        public id?: number,
        public ferbrile?: boolean,
        public pillar?: boolean,
        public dispensys?: boolean,
        public rR?: string,
        public clearLungs?: boolean,
        public ronchi?: boolean,
        public crackles?: boolean,
        public otherRS?: any,
        public hR?: string,
        public regular?: boolean,
        public murmurs?: boolean,
        public otherCVS?: any,
        public softAbdomen?: boolean,
        public tense?: boolean,
        public tender?: boolean,
        public noneTender?: boolean,
        public otherAbdomen?: any,
        public neurology?: any,
        public otherSystems?: any,
        public recNo?: string,
        public date?: any,
        public user?: User,
        public clinic?: Clinic,
        public investigation?: Investigation,
    ) {
        this.ferbrile = false;
        this.pillar = false;
        this.dispensys = false;
        this.clearLungs = false;
        this.ronchi = false;
        this.crackles = false;
        this.regular = false;
        this.murmurs = false;
        this.softAbdomen = false;
        this.tense = false;
        this.tender = false;
        this.noneTender = false;
    }
}
