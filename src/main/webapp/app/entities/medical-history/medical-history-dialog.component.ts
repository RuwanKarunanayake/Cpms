import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { MedicalHistory } from './medical-history.model';
import { MedicalHistoryPopupService } from './medical-history-popup.service';
import { MedicalHistoryService } from './medical-history.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-medical-history-dialog',
    templateUrl: './medical-history-dialog.component.html'
})
export class MedicalHistoryDialogComponent implements OnInit {

    medicalHistory: MedicalHistory;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private medicalHistoryService: MedicalHistoryService,
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
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, medicalHistory, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                medicalHistory[field] = base64Data;
                medicalHistory[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.medicalHistory.id !== undefined) {
            this.medicalHistoryService.update(this.medicalHistory)
                .subscribe((res: MedicalHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.medicalHistoryService.create(this.medicalHistory)
                .subscribe((res: MedicalHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: MedicalHistory) {
        this.eventManager.broadcast({ name: 'medicalHistoryListModification', content: 'OK'});
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
    selector: 'jhi-medical-history-popup',
    template: ''
})
export class MedicalHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private medicalHistoryPopupService: MedicalHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.medicalHistoryPopupService
                    .open(MedicalHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.medicalHistoryPopupService
                    .open(MedicalHistoryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
