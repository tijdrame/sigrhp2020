import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMembreFamille } from 'app/shared/model/membre-famille.model';

type EntityResponseType = HttpResponse<IMembreFamille>;
type EntityArrayResponseType = HttpResponse<IMembreFamille[]>;

@Injectable({ providedIn: 'root' })
export class MembreFamilleService {
  public resourceUrl = SERVER_API_URL + 'api/membre-familles';

  constructor(protected http: HttpClient) {}

  create(membreFamille: IMembreFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membreFamille);
    return this.http
      .post<IMembreFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(membreFamille: IMembreFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membreFamille);
    return this.http
      .put<IMembreFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMembreFamille>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembreFamille[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(membreFamille: IMembreFamille): IMembreFamille {
    const copy: IMembreFamille = Object.assign({}, membreFamille, {
      dateNaissance:
        membreFamille.dateNaissance && membreFamille.dateNaissance.isValid() ? membreFamille.dateNaissance.format(DATE_FORMAT) : undefined,
      dateMariage:
        membreFamille.dateMariage && membreFamille.dateMariage.isValid() ? membreFamille.dateMariage.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? moment(res.body.dateNaissance) : undefined;
      res.body.dateMariage = res.body.dateMariage ? moment(res.body.dateMariage) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((membreFamille: IMembreFamille) => {
        membreFamille.dateNaissance = membreFamille.dateNaissance ? moment(membreFamille.dateNaissance) : undefined;
        membreFamille.dateMariage = membreFamille.dateMariage ? moment(membreFamille.dateMariage) : undefined;
      });
    }
    return res;
  }
}
