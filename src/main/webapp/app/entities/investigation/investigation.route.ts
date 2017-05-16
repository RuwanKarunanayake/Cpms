import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { InvestigationComponent } from './investigation.component';
import { InvestigationDetailComponent } from './investigation-detail.component';
import { InvestigationPopupComponent } from './investigation-dialog.component';
import { InvestigationDeletePopupComponent } from './investigation-delete-dialog.component';

import { Principal } from '../../shared';


export const investigationRoute: Routes = [
  {
    path: 'investigation',
    component: InvestigationComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Investigations'
    }
  }, {
    path: 'investigation/:id',
    component: InvestigationDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Investigations'
    }
  }
];

export const investigationPopupRoute: Routes = [
  {
    path: 'investigation-new',
    component: InvestigationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Investigations'
    },
    outlet: 'popup'
  },
  {
    path: 'investigation/:id/edit',
    component: InvestigationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Investigations'
    },
    outlet: 'popup'
  },
  {
    path: 'investigation/:id/delete',
    component: InvestigationDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Investigations'
    },
    outlet: 'popup'
  }
];
