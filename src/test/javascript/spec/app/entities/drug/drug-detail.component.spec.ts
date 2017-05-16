import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DrugDetailComponent } from '../../../../../../main/webapp/app/entities/drug/drug-detail.component';
import { DrugService } from '../../../../../../main/webapp/app/entities/drug/drug.service';
import { Drug } from '../../../../../../main/webapp/app/entities/drug/drug.model';

describe('Component Tests', () => {

    describe('Drug Management Detail Component', () => {
        let comp: DrugDetailComponent;
        let fixture: ComponentFixture<DrugDetailComponent>;
        let service: DrugService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DrugDetailComponent],
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
                    DrugService
                ]
            }).overrideComponent(DrugDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Drug(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.drug).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
