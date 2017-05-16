import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Userinfo } from './userinfo.model';
import { UserinfoService } from './userinfo.service';

@Component({
    selector: 'jhi-userinfo-detail',
    templateUrl: './userinfo-detail.component.html'
})
export class UserinfoDetailComponent implements OnInit, OnDestroy {

    userinfo: Userinfo;
    private subscription: any;

    constructor(
        private userinfoService: UserinfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.userinfoService.find(id).subscribe(userinfo => {
            this.userinfo = userinfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
