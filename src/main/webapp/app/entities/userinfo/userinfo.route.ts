import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserinfoComponent } from './userinfo.component';
import { UserinfoDetailComponent } from './userinfo-detail.component';
import { UserinfoPopupComponent } from './userinfo-dialog.component';
import { UserinfoDeletePopupComponent } from './userinfo-delete-dialog.component';

import { Principal } from '../../shared';


export const userinfoRoute: Routes = [
  {
    path: 'userinfo',
    component: UserinfoComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Userinfos'
    }
  }, {
    path: 'userinfo/:id',
    component: UserinfoDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Userinfos'
    }
  }
];

export const userinfoPopupRoute: Routes = [
  {
    path: 'userinfo-new',
    component: UserinfoPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Userinfos'
    },
    outlet: 'popup'
  },
  {
    path: 'userinfo/:id/edit',
    component: UserinfoPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Userinfos'
    },
    outlet: 'popup'
  },
  {
    path: 'userinfo/:id/delete',
    component: UserinfoDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Userinfos'
    },
    outlet: 'popup'
  }
];
