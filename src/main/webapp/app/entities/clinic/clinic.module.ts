import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpmsSharedModule } from '../../shared';

import {
    ClinicService,
    ClinicPopupService,
    ClinicComponent,
    ClinicDetailComponent,
    ClinicDialogComponent,
    ClinicPopupComponent,
    ClinicDeletePopupComponent,
    ClinicDeleteDialogComponent,
    clinicRoute,
    clinicPopupRoute,
} from './';

let ENTITY_STATES = [
    ...clinicRoute,
    ...clinicPopupRoute,
];

@NgModule({
    imports: [
        CpmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClinicComponent,
        ClinicDetailComponent,
        ClinicDialogComponent,
        ClinicDeleteDialogComponent,
        ClinicPopupComponent,
        ClinicDeletePopupComponent,
    ],
    entryComponents: [
        ClinicComponent,
        ClinicDialogComponent,
        ClinicPopupComponent,
        ClinicDeleteDialogComponent,
        ClinicDeletePopupComponent,
    ],
    providers: [
        ClinicService,
        ClinicPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsClinicModule {}
