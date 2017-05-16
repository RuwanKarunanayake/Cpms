import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { Investigation } from './investigation.model';
import { InvestigationPopupService } from './investigation-popup.service';
import { InvestigationService } from './investigation.service';
import { Recording, RecordingService } from '../recording';

@Component({
    selector: 'jhi-investigation-dialog',
    templateUrl: './investigation-dialog.component.html'
})
export class InvestigationDialogComponent implements OnInit {

    investigation: Investigation;
    authorities: any[];
    isSaving: boolean;

    recordings: Recording[];
    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private investigationService: InvestigationService,
        private recordingService: RecordingService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.recordingService.query({filter: 'investigation-is-null'}).subscribe((res: Response) => {
            if (!this.investigation.recording || !this.investigation.recording.id) {
                this.recordings = res.json();
            } else {
                this.recordingService.find(this.investigation.recording.id).subscribe((subRes: Recording) => {
                    this.recordings = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, investigation, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                investigation[field] = base64Data;
                investigation[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.investigation.id !== undefined) {
            this.investigationService.update(this.investigation)
                .subscribe((res: Investigation) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.investigationService.create(this.investigation)
                .subscribe((res: Investigation) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Investigation) {
        this.eventManager.broadcast({ name: 'investigationListModification', content: 'OK'});
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

    trackRecordingById(index: number, item: Recording) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-investigation-popup',
    template: ''
})
export class InvestigationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private investigationPopupService: InvestigationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.investigationPopupService
                    .open(InvestigationDialogComponent, params['id']);
            } else {
                this.modalRef = this.investigationPopupService
                    .open(InvestigationDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
