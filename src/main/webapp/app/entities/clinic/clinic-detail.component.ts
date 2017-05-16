import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Clinic } from './clinic.model';
import { ClinicService } from './clinic.service';

@Component({
    selector: 'jhi-clinic-detail',
    templateUrl: './clinic-detail.component.html'
})
export class ClinicDetailComponent implements OnInit, OnDestroy {

    clinic: Clinic;
    private subscription: any;

    constructor(
        private clinicService: ClinicService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.clinicService.find(id).subscribe(clinic => {
            this.clinic = clinic;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
