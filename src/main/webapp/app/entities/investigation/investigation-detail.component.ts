import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'ng-jhipster';
import { Investigation } from './investigation.model';
import { InvestigationService } from './investigation.service';

@Component({
    selector: 'jhi-investigation-detail',
    templateUrl: './investigation-detail.component.html'
})
export class InvestigationDetailComponent implements OnInit, OnDestroy {

    investigation: Investigation;
    private subscription: any;

    constructor(
        private dataUtils: DataUtils,
        private investigationService: InvestigationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.investigationService.find(id).subscribe(investigation => {
            this.investigation = investigation;
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
