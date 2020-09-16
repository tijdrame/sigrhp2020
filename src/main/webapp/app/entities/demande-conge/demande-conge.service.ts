import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDemandeConge } from 'app/shared/model/demande-conge.model';

type EntityResponseType = HttpResponse<IDemandeConge>;
type EntityArrayResponseType = HttpResponse<IDemandeConge[]>;

@Injectable({ providedIn: 'root' })
export class DemandeCongeService {
  public resourceUrl = SERVER_API_URL + 'api/demande-conges';

  constructor(protected http: HttpClient) {}

  create(demandeConge: IDemandeConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeConge);
    return this.http
      .post<IDemandeConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demandeConge: IDemandeConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeConge);
    return this.http
      .put<IDemandeConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemandeConge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemandeConge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(demandeConge: IDemandeConge): IDemandeConge {
    const copy: IDemandeConge = Object.assign({}, demandeConge, {
      dateDebut: demandeConge.dateDebut && demandeConge.dateDebut.isValid() ? demandeConge.dateDebut.format(DATE_FORMAT) : undefined,
      dateFin: demandeConge.dateFin && demandeConge.dateFin.isValid() ? demandeConge.dateFin.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? moment(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((demandeConge: IDemandeConge) => {
        demandeConge.dateDebut = demandeConge.dateDebut ? moment(demandeConge.dateDebut) : undefined;
        demandeConge.dateFin = demandeConge.dateFin ? moment(demandeConge.dateFin) : undefined;
      });
    }
    return res;
  }
}
