import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService, DataUtils } from 'ng-jhipster';

import { MedicalHistory } from './medical-history.model';
import { MedicalHistoryService } from './medical-history.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-medical-history',
    templateUrl: './medical-history.component.html'
})
export class MedicalHistoryComponent implements OnInit, OnDestroy {
medicalHistories: MedicalHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private medicalHistoryService: MedicalHistoryService,
        private alertService: AlertService,
        private dataUtils: DataUtils,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.medicalHistoryService.query().subscribe(
            (res: Response) => {
                this.medicalHistories = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMedicalHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: MedicalHistory) {
        return item.id;
    }



    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMedicalHistories() {
        this.eventSubscriber = this.eventManager.subscribe('medicalHistoryListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
