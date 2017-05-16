import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpmsSharedModule } from '../../shared';
import { CpmsAdminModule } from '../../admin/admin.module';

import {
    UserinfoService,
    UserinfoPopupService,
    UserinfoComponent,
    UserinfoDetailComponent,
    UserinfoDialogComponent,
    UserinfoPopupComponent,
    UserinfoDeletePopupComponent,
    UserinfoDeleteDialogComponent,
    userinfoRoute,
    userinfoPopupRoute,
} from './';

let ENTITY_STATES = [
    ...userinfoRoute,
    ...userinfoPopupRoute,
];

@NgModule({
    imports: [
        CpmsSharedModule,
        CpmsAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserinfoComponent,
        UserinfoDetailComponent,
        UserinfoDialogComponent,
        UserinfoDeleteDialogComponent,
        UserinfoPopupComponent,
        UserinfoDeletePopupComponent,
    ],
    entryComponents: [
        UserinfoComponent,
        UserinfoDialogComponent,
        UserinfoPopupComponent,
        UserinfoDeleteDialogComponent,
        UserinfoDeletePopupComponent,
    ],
    providers: [
        UserinfoService,
        UserinfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmsUserinfoModule {}
