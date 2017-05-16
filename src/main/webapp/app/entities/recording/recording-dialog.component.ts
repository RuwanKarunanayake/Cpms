import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { Recording } from './recording.model';
import { RecordingPopupService } from './recording-popup.service';
import { RecordingService } from './recording.service';
import { User, UserService } from '../../shared';
import { Clinic, ClinicService } from '../clinic';
import { Investigation, InvestigationService } from '../investigation';

@Component({
    selector: 'jhi-recording-dialog',
    templateUrl: './recording-dialog.component.html'
})
export class RecordingDialogComponent implements OnInit {

    recording: Recording;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    clinics: Clinic[];

    investigations: Investigation[];
    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private recordingService: RecordingService,
        private userService: UserService,
        private clinicService: ClinicService,
        private investigationService: InvestigationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.clinicService.query().subscribe(
            (res: Response) => { this.clinics = res.json(); }, (res: Response) => this.onError(res.json()));
        this.investigationService.query().subscribe(
            (res: Response) => { this.investigations = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, recording, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                recording[field] = base64Data;
                recording[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.recording.id !== undefined) {
            this.recordingService.update(this.recording)
                .subscribe((res: Recording) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.recordingService.create(this.recording)
                .subscribe((res: Recording) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Recording) {
        this.eventManager.broadcast({ name: 'recordingListModification', content: 'OK'});
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

    trackClinicById(index: number, item: Clinic) {
        return item.id;
    }

    trackInvestigationById(index: number, item: Investigation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-recording-popup',
    template: ''
})
export class RecordingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private recordingPopupService: RecordingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.recordingPopupService
                    .open(RecordingDialogComponent, params['id']);
            } else {
                this.modalRef = this.recordingPopupService
                    .open(RecordingDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
