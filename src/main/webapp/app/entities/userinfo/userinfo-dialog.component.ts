import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Userinfo } from './userinfo.model';
import { UserinfoPopupService } from './userinfo-popup.service';
import { UserinfoService } from './userinfo.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-userinfo-dialog',
    templateUrl: './userinfo-dialog.component.html'
})
export class UserinfoDialogComponent implements OnInit {

    userinfo: Userinfo;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private userinfoService: UserinfoService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.userinfo.id !== undefined) {
            this.userinfoService.update(this.userinfo)
                .subscribe((res: Userinfo) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.userinfoService.create(this.userinfo)
                .subscribe((res: Userinfo) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Userinfo) {
        this.eventManager.broadcast({ name: 'userinfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-userinfo-popup',
    template: ''
})
export class UserinfoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private userinfoPopupService: UserinfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.userinfoPopupService
                    .open(UserinfoDialogComponent, params['id']);
            } else {
                this.modalRef = this.userinfoPopupService
                    .open(UserinfoDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
