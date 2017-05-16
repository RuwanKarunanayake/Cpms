import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'ng-jhipster';
import { Recording } from './recording.model';
import { RecordingService } from './recording.service';

@Component({
    selector: 'jhi-recording-detail',
    templateUrl: './recording-detail.component.html'
})
export class RecordingDetailComponent implements OnInit, OnDestroy {

    recording: Recording;
    private subscription: any;

    constructor(
        private dataUtils: DataUtils,
        private recordingService: RecordingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.recordingService.find(id).subscribe(recording => {
            this.recording = recording;
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
