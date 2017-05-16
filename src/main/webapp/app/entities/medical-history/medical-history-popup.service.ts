import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MedicalHistory } from './medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
@Injectable()
export class MedicalHistoryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private medicalHistoryService: MedicalHistoryService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.medicalHistoryService.find(id).subscribe(medicalHistory => {
                this.medicalHistoryModalRef(component, medicalHistory);
            });
        } else {
            return this.medicalHistoryModalRef(component, new MedicalHistory());
        }
    }

    medicalHistoryModalRef(component: Component, medicalHistory: MedicalHistory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medicalHistory = medicalHistory;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
