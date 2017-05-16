import { User } from '../../shared';
export class MedicalHistory {
    constructor(
        public id?: number,
        public diabates?: boolean,
        public hypertension?: boolean,
        public ihd?: boolean,
        public ba?: boolean,
        public ckd?: boolean,
        public epilepsy?: boolean,
        public otherPast?: any,
        public pastSurgical?: any,
        public pastAllergy?: any,
        public pastDrug?: any,
        public pastFamily?: any,
        public smoke?: boolean,
        public alchohol?: boolean,
        public otherHistory?: any,
        public user?: User,
    ) {
        this.diabates = false;
        this.hypertension = false;
        this.ihd = false;
        this.ba = false;
        this.ckd = false;
        this.epilepsy = false;
        this.smoke = false;
        this.alchohol = false;
    }
}
