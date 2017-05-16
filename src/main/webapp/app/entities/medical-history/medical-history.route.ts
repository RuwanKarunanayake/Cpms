import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MedicalHistoryComponent } from './medical-history.component';
import { MedicalHistoryDetailComponent } from './medical-history-detail.component';
import { MedicalHistoryPopupComponent } from './medical-history-dialog.component';
import { MedicalHistoryDeletePopupComponent } from './medical-history-delete-dialog.component';

import { Principal } from '../../shared';


export const medicalHistoryRoute: Routes = [
  {
    path: 'medical-history',
    component: MedicalHistoryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MedicalHistories'
    }
  }, {
    path: 'medical-history/:id',
    component: MedicalHistoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MedicalHistories'
    }
  }
];

export const medicalHistoryPopupRoute: Routes = [
  {
    path: 'medical-history-new',
    component: MedicalHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MedicalHistories'
    },
    outlet: 'popup'
  },
  {
    path: 'medical-history/:id/edit',
    component: MedicalHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MedicalHistories'
    },
    outlet: 'popup'
  },
  {
    path: 'medical-history/:id/delete',
    component: MedicalHistoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MedicalHistories'
    },
    outlet: 'popup'
  }
];
