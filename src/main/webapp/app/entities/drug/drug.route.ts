import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DrugComponent } from './drug.component';
import { DrugDetailComponent } from './drug-detail.component';
import { DrugPopupComponent } from './drug-dialog.component';
import { DrugDeletePopupComponent } from './drug-delete-dialog.component';

import { Principal } from '../../shared';


export const drugRoute: Routes = [
  {
    path: 'drug',
    component: DrugComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Drugs'
    }
  }, {
    path: 'drug/:id',
    component: DrugDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Drugs'
    }
  }
];

export const drugPopupRoute: Routes = [
  {
    path: 'drug-new',
    component: DrugPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Drugs'
    },
    outlet: 'popup'
  },
  {
    path: 'drug/:id/edit',
    component: DrugPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Drugs'
    },
    outlet: 'popup'
  },
  {
    path: 'drug/:id/delete',
    component: DrugDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Drugs'
    },
    outlet: 'popup'
  }
];
