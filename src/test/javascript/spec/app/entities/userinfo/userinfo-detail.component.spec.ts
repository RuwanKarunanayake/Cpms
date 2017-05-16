import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserinfoDetailComponent } from '../../../../../../main/webapp/app/entities/userinfo/userinfo-detail.component';
import { UserinfoService } from '../../../../../../main/webapp/app/entities/userinfo/userinfo.service';
import { Userinfo } from '../../../../../../main/webapp/app/entities/userinfo/userinfo.model';

describe('Component Tests', () => {

    describe('Userinfo Management Detail Component', () => {
        let comp: UserinfoDetailComponent;
        let fixture: ComponentFixture<UserinfoDetailComponent>;
        let service: UserinfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [UserinfoDetailComponent],
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
                    UserinfoService
                ]
            }).overrideComponent(UserinfoDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserinfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserinfoService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Userinfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userinfo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
