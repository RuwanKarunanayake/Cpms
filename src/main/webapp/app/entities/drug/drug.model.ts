export class Drug {
    constructor(
        public id?: number,
        public name?: string,
        public available?: boolean,
    ) {
        this.available = false;
    }
}
