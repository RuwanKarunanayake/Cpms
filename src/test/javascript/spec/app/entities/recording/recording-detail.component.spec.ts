import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RecordingDetailComponent } from '../../../../../../main/webapp/app/entities/recording/recording-detail.component';
import { RecordingService } from '../../../../../../main/webapp/app/entities/recording/recording.service';
import { Recording } from '../../../../../../main/webapp/app/entities/recording/recording.model';

describe('Component Tests', () => {

    describe('Recording Management Detail Component', () => {
        let comp: RecordingDetailComponent;
        let fixture: ComponentFixture<RecordingDetailComponent>;
        let service: RecordingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [RecordingDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    RecordingService
                ]
            }).overrideComponent(RecordingDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecordingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Recording(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.recording).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
