import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Userinfo } from './userinfo.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class UserinfoService {

    private resourceUrl = 'api/userinfos';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(userinfo: Userinfo): Observable<Userinfo> {
        let copy: Userinfo = Object.assign({}, userinfo);
        copy.dob = this.dateUtils
            .convertLocalDateToServer(userinfo.dob);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(userinfo: Userinfo): Observable<Userinfo> {
        let copy: Userinfo = Object.assign({}, userinfo);
        copy.dob = this.dateUtils
            .convertLocalDateToServer(userinfo.dob);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Userinfo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.dob = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dob);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dob = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dob);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
