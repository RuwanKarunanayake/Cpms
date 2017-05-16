import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Recording } from './recording.model';
import { RecordingService } from './recording.service';
@Injectable()
export class RecordingPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private recordingService: RecordingService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.recordingService.find(id).subscribe(recording => {
                if (recording.date) {
                    recording.date = {
                        year: recording.date.getFullYear(),
                        month: recording.date.getMonth() + 1,
                        day: recording.date.getDate()
                    };
                }
                this.recordingModalRef(component, recording);
            });
        } else {
            return this.recordingModalRef(component, new Recording());
        }
    }

    recordingModalRef(component: Component, recording: Recording): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recording = recording;
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
