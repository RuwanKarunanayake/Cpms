import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RecordingComponent } from './recording.component';
import { RecordingDetailComponent } from './recording-detail.component';
import { RecordingPopupComponent } from './recording-dialog.component';
import { RecordingDeletePopupComponent } from './recording-delete-dialog.component';

import { Principal } from '../../shared';


export const recordingRoute: Routes = [
  {
    path: 'recording',
    component: RecordingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Recordings'
    }
  }, {
    path: 'recording/:id',
    component: RecordingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Recordings'
    }
  }
];

export const recordingPopupRoute: Routes = [
  {
    path: 'recording-new',
    component: RecordingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Recordings'
    },
    outlet: 'popup'
  },
  {
    path: 'recording/:id/edit',
    component: RecordingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Recordings'
    },
    outlet: 'popup'
  },
  {
    path: 'recording/:id/delete',
    component: RecordingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Recordings'
    },
    outlet: 'popup'
  }
];
