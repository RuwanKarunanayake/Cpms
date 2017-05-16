import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MedicalHistory } from './medical-history.model';
import { MedicalHistoryPopupService } from './medical-history-popup.service';
import { MedicalHistoryService } from './medical-history.service';

@Component({
    selector: 'jhi-medical-history-delete-dialog',
    templateUrl: './medical-history-delete-dialog.component.html'
})
export class MedicalHistoryDeleteDialogComponent {

    medicalHistory: MedicalHistory;

    constructor(
        private medicalHistoryService: MedicalHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.medicalHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'medicalHistoryListModification',
                content: 'Deleted an medicalHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medical-history-delete-popup',
    template: ''
})
export class MedicalHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private medicalHistoryPopupService: MedicalHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.medicalHistoryPopupService
                .open(MedicalHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
