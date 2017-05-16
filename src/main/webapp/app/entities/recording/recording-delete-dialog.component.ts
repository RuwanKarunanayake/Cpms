import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Recording } from './recording.model';
import { RecordingPopupService } from './recording-popup.service';
import { RecordingService } from './recording.service';

@Component({
    selector: 'jhi-recording-delete-dialog',
    templateUrl: './recording-delete-dialog.component.html'
})
export class RecordingDeleteDialogComponent {

    recording: Recording;

    constructor(
        private recordingService: RecordingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.recordingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recordingListModification',
                content: 'Deleted an recording'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recording-delete-popup',
    template: ''
})
export class RecordingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private recordingPopupService: RecordingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.recordingPopupService
                .open(RecordingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
