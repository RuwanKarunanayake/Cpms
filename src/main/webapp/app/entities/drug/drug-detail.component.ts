import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Drug } from './drug.model';
import { DrugService } from './drug.service';

@Component({
    selector: 'jhi-drug-detail',
    templateUrl: './drug-detail.component.html'
})
export class DrugDetailComponent implements OnInit, OnDestroy {

    drug: Drug;
    private subscription: any;

    constructor(
        private drugService: DrugService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.drugService.find(id).subscribe(drug => {
            this.drug = drug;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
