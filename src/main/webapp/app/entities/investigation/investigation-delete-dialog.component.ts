import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Investigation } from './investigation.model';
import { InvestigationPopupService } from './investigation-popup.service';
import { InvestigationService } from './investigation.service';

@Component({
    selector: 'jhi-investigation-delete-dialog',
    templateUrl: './investigation-delete-dialog.component.html'
})
export class InvestigationDeleteDialogComponent {

    investigation: Investigation;

    constructor(
        private investigationService: InvestigationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.investigationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'investigationListModification',
                content: 'Deleted an investigation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-investigation-delete-popup',
    template: ''
})
export class InvestigationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private investigationPopupService: InvestigationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.investigationPopupService
                .open(InvestigationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
