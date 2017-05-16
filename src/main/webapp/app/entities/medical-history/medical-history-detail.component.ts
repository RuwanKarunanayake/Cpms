import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'ng-jhipster';
import { MedicalHistory } from './medical-history.model';
import { MedicalHistoryService } from './medical-history.service';

@Component({
    selector: 'jhi-medical-history-detail',
    templateUrl: './medical-history-detail.component.html'
})
export class MedicalHistoryDetailComponent implements OnInit, OnDestroy {

    medicalHistory: MedicalHistory;
    private subscription: any;

    constructor(
        private dataUtils: DataUtils,
        private medicalHistoryService: MedicalHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.medicalHistoryService.find(id).subscribe(medicalHistory => {
            this.medicalHistory = medicalHistory;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
