import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpmsSharedModule } from '../../shared';

import {
    InvestigationService,
    InvestigationPopupService,
    InvestigationComponent,
    InvestigationDetailComponent,
    InvestigationDialogComponent,
    InvestigationPopupComponent,
    InvestigationDeletePopupComponent,
    InvestigationDeleteDialogComponent,
    investigationRoute,
    investigationPopupRoute,
} from './';

let ENTITY_STATES = [
    ...investigationRoute,
    ...investigationPopupRoute,
];

@NgModule({
    imports: [
        CpmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InvestigationComponent,
        InvestigationDetailComponent,
        InvestigationDialogComponent,
        InvestigationDeleteDialogComponent,
        InvestigationPopupComponent,
        InvestigationDeletePopupComponent,
    ],
    entryComponents: [
        InvestigationComponent,
        InvestigationDialogComponent,
        InvestigationPopupComponent,
        InvestigationDeleteDialogComponent,
        InvestigationDeletePopupComponent,
    ],
    providers: [
        InvestigationService,
        InvestigationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsInvestigationModule {}
