import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MedicalHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/medical-history/medical-history-detail.component';
import { MedicalHistoryService } from '../../../../../../main/webapp/app/entities/medical-history/medical-history.service';
import { MedicalHistory } from '../../../../../../main/webapp/app/entities/medical-history/medical-history.model';

describe('Component Tests', () => {

    describe('MedicalHistory Management Detail Component', () => {
        let comp: MedicalHistoryDetailComponent;
        let fixture: ComponentFixture<MedicalHistoryDetailComponent>;
        let service: MedicalHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MedicalHistoryDetailComponent],
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
                    MedicalHistoryService
                ]
            }).overrideComponent(MedicalHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicalHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicalHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MedicalHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.medicalHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
