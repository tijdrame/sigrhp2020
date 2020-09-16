import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRemboursement } from 'app/shared/model/remboursement.model';

type EntityResponseType = HttpResponse<IRemboursement>;
type EntityArrayResponseType = HttpResponse<IRemboursement[]>;

@Injectable({ providedIn: 'root' })
export class RemboursementService {
  public resourceUrl = SERVER_API_URL + 'api/remboursements';

  constructor(protected http: HttpClient) {}

  create(remboursement: IRemboursement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remboursement);
    return this.http
      .post<IRemboursement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(remboursement: IRemboursement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remboursement);
    return this.http
      .put<IRemboursement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRemboursement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRemboursement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(remboursement: IRemboursement): IRemboursement {
    const copy: IRemboursement = Object.assign({}, remboursement, {
      dateRemboursement:
        remboursement.dateRemboursement && remboursement.dateRemboursement.isValid()
          ? remboursement.dateRemboursement.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateRemboursement = res.body.dateRemboursement ? moment(res.body.dateRemboursement) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((remboursement: IRemboursement) => {
        remboursement.dateRemboursement = remboursement.dateRemboursement ? moment(remboursement.dateRemboursement) : undefined;
      });
    }
    return res;
  }
}
