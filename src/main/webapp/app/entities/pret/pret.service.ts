import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPret } from 'app/shared/model/pret.model';

type EntityResponseType = HttpResponse<IPret>;
type EntityArrayResponseType = HttpResponse<IPret[]>;

@Injectable({ providedIn: 'root' })
export class PretService {
  public resourceUrl = SERVER_API_URL + 'api/prets';

  constructor(protected http: HttpClient) {}

  create(pret: IPret): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pret);
    return this.http
      .post<IPret>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pret: IPret): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pret);
    return this.http
      .put<IPret>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPret>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPret[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pret: IPret): IPret {
    const copy: IPret = Object.assign({}, pret, {
      datePret: pret.datePret && pret.datePret.isValid() ? pret.datePret.format(DATE_FORMAT) : undefined,
      dateDebutRemboursement:
        pret.dateDebutRemboursement && pret.dateDebutRemboursement.isValid() ? pret.dateDebutRemboursement.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datePret = res.body.datePret ? moment(res.body.datePret) : undefined;
      res.body.dateDebutRemboursement = res.body.dateDebutRemboursement ? moment(res.body.dateDebutRemboursement) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pret: IPret) => {
        pret.datePret = pret.datePret ? moment(pret.datePret) : undefined;
        pret.dateDebutRemboursement = pret.dateDebutRemboursement ? moment(pret.dateDebutRemboursement) : undefined;
      });
    }
    return res;
  }
}
