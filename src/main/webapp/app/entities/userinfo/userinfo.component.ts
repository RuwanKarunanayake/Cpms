import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Userinfo } from './userinfo.model';
import { UserinfoService } from './userinfo.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-userinfo',
    templateUrl: './userinfo.component.html'
})
export class UserinfoComponent implements OnInit, OnDestroy {
userinfos: Userinfo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userinfoService: UserinfoService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userinfoService.query().subscribe(
            (res: Response) => {
                this.userinfos = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserinfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Userinfo) {
        return item.id;
    }



    registerChangeInUserinfos() {
        this.eventSubscriber = this.eventManager.subscribe('userinfoListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
