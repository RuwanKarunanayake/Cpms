import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpmsSharedModule } from '../../shared';
import { CpmsAdminModule } from '../../admin/admin.module';

import {
    MedicalHistoryService,
    MedicalHistoryPopupService,
    MedicalHistoryComponent,
    MedicalHistoryDetailComponent,
    MedicalHistoryDialogComponent,
    MedicalHistoryPopupComponent,
    MedicalHistoryDeletePopupComponent,
    MedicalHistoryDeleteDialogComponent,
    medicalHistoryRoute,
    medicalHistoryPopupRoute,
} from './';

let ENTITY_STATES = [
    ...medicalHistoryRoute,
    ...medicalHistoryPopupRoute,
];

@NgModule({
    imports: [
        CpmsSharedModule,
        CpmsAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MedicalHistoryComponent,
        MedicalHistoryDetailComponent,
        MedicalHistoryDialogComponent,
        MedicalHistoryDeleteDialogComponent,
        MedicalHistoryPopupComponent,
        MedicalHistoryDeletePopupComponent,
    ],
    entryComponents: [
        MedicalHistoryComponent,
        MedicalHistoryDialogComponent,
        MedicalHistoryPopupComponent,
        MedicalHistoryDeleteDialogComponent,
        MedicalHistoryDeletePopupComponent,
    ],
    providers: [
        MedicalHistoryService,
        MedicalHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsMedicalHistoryModule {}
