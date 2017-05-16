import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InvestigationDetailComponent } from '../../../../../../main/webapp/app/entities/investigation/investigation-detail.component';
import { InvestigationService } from '../../../../../../main/webapp/app/entities/investigation/investigation.service';
import { Investigation } from '../../../../../../main/webapp/app/entities/investigation/investigation.model';

describe('Component Tests', () => {

    describe('Investigation Management Detail Component', () => {
        let comp: InvestigationDetailComponent;
        let fixture: ComponentFixture<InvestigationDetailComponent>;
        let service: InvestigationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [InvestigationDetailComponent],
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
                    InvestigationService
                ]
            }).overrideComponent(InvestigationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InvestigationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvestigationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Investigation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.investigation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
