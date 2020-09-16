import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAbsence } from 'app/shared/model/absence.model';

type EntityResponseType = HttpResponse<IAbsence>;
type EntityArrayResponseType = HttpResponse<IAbsence[]>;

@Injectable({ providedIn: 'root' })
export class AbsenceService {
  public resourceUrl = SERVER_API_URL + 'api/absences';

  constructor(protected http: HttpClient) {}

  create(absence: IAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(absence);
    return this.http
      .post<IAbsence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(absence: IAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(absence);
    return this.http
      .put<IAbsence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAbsence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAbsence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(absence: IAbsence): IAbsence {
    const copy: IAbsence = Object.assign({}, absence, {
      dateAbsence: absence.dateAbsence && absence.dateAbsence.isValid() ? absence.dateAbsence.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAbsence = res.body.dateAbsence ? moment(res.body.dateAbsence) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((absence: IAbsence) => {
        absence.dateAbsence = absence.dateAbsence ? moment(absence.dateAbsence) : undefined;
      });
    }
    return res;
  }
}
