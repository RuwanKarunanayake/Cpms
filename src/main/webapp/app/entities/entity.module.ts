import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CpmsUserinfoModule } from './userinfo/userinfo.module';
import { CpmsMedicalHistoryModule } from './medical-history/medical-history.module';
import { CpmsClinicModule } from './clinic/clinic.module';
import { CpmsRecordingModule } from './recording/recording.module';
import { CpmsInvestigationModule } from './investigation/investigation.module';
import { CpmsDrugModule } from './drug/drug.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CpmsUserinfoModule,
        CpmsMedicalHistoryModule,
        CpmsClinicModule,
        CpmsRecordingModule,
        CpmsInvestigationModule,
        CpmsDrugModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsEntityModule {}
