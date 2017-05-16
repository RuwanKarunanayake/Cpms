import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Userinfo } from './userinfo.model';
import { UserinfoPopupService } from './userinfo-popup.service';
import { UserinfoService } from './userinfo.service';

@Component({
    selector: 'jhi-userinfo-delete-dialog',
    templateUrl: './userinfo-delete-dialog.component.html'
})
export class UserinfoDeleteDialogComponent {

    userinfo: Userinfo;

    constructor(
        private userinfoService: UserinfoService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.userinfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userinfoListModification',
                content: 'Deleted an userinfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-userinfo-delete-popup',
    template: ''
})
export class UserinfoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private userinfoPopupService: UserinfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.userinfoPopupService
                .open(UserinfoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
