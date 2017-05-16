import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Userinfo } from './userinfo.model';
import { UserinfoService } from './userinfo.service';
@Injectable()
export class UserinfoPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private userinfoService: UserinfoService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.userinfoService.find(id).subscribe(userinfo => {
                if (userinfo.dob) {
                    userinfo.dob = {
                        year: userinfo.dob.getFullYear(),
                        month: userinfo.dob.getMonth() + 1,
                        day: userinfo.dob.getDate()
                    };
                }
                this.userinfoModalRef(component, userinfo);
            });
        } else {
            return this.userinfoModalRef(component, new Userinfo());
        }
    }

    userinfoModalRef(component: Component, userinfo: Userinfo): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userinfo = userinfo;
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
