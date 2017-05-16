import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpmsSharedModule } from '../../shared';
import { CpmsAdminModule } from '../../admin/admin.module';

import {
    RecordingService,
    RecordingPopupService,
    RecordingComponent,
    RecordingDetailComponent,
    RecordingDialogComponent,
    RecordingPopupComponent,
    RecordingDeletePopupComponent,
    RecordingDeleteDialogComponent,
    recordingRoute,
    recordingPopupRoute,
} from './';

let ENTITY_STATES = [
    ...recordingRoute,
    ...recordingPopupRoute,
];

@NgModule({
    imports: [
        CpmsSharedModule,
        CpmsAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RecordingComponent,
        RecordingDetailComponent,
        RecordingDialogComponent,
        RecordingDeleteDialogComponent,
        RecordingPopupComponent,
        RecordingDeletePopupComponent,
    ],
    entryComponents: [
        RecordingComponent,
        RecordingDialogComponent,
        RecordingPopupComponent,
        RecordingDeleteDialogComponent,
        RecordingDeletePopupComponent,
    ],
    providers: [
        RecordingService,
        RecordingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsRecordingModule {}
