import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpmsSharedModule } from '../../shared';

import {
    DrugService,
    DrugPopupService,
    DrugComponent,
    DrugDetailComponent,
    DrugDialogComponent,
    DrugPopupComponent,
    DrugDeletePopupComponent,
    DrugDeleteDialogComponent,
    drugRoute,
    drugPopupRoute,
} from './';

let ENTITY_STATES = [
    ...drugRoute,
    ...drugPopupRoute,
];

@NgModule({
    imports: [
        CpmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DrugComponent,
        DrugDetailComponent,
        DrugDialogComponent,
        DrugDeleteDialogComponent,
        DrugPopupComponent,
        DrugDeletePopupComponent,
    ],
    entryComponents: [
        DrugComponent,
        DrugDialogComponent,
        DrugPopupComponent,
        DrugDeleteDialogComponent,
        DrugDeletePopupComponent,
    ],
    providers: [
        DrugService,
        DrugPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsDrugModule {}
