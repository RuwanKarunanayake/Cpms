import { Recording } from '../recording';
export class Investigation {
    constructor(
        public id?: number,
        public fBC?: boolean,
        public bV?: boolean,
        public sE?: boolean,
        public sCV?: boolean,
        public lFT?: boolean,
        public lipidProfile?: boolean,
        public fBS?: boolean,
        public pPBS?: boolean,
        public bloodTestCompleted?: boolean,
        public uFR?: boolean,
        public urineTestCompleted?: boolean,
        public culture?: boolean,
        public cultureTestCompleted?: boolean,
        public other?: any,
        public recording?: Recording,
    ) {
        this.fBC = false;
        this.bV = false;
        this.sE = false;
        this.sCV = false;
        this.lFT = false;
        this.lipidProfile = false;
        this.fBS = false;
        this.pPBS = false;
        this.bloodTestCompleted = false;
        this.uFR = false;
        this.urineTestCompleted = false;
        this.culture = false;
        this.cultureTestCompleted = false;
    }
}
