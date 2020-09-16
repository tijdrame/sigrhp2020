import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';

type EntityResponseType = HttpResponse<ICollaborateur>;
type EntityArrayResponseType = HttpResponse<ICollaborateur[]>;

@Injectable({ providedIn: 'root' })
export class CollaborateurService {
  public resourceUrl = SERVER_API_URL + 'api/collaborateurs';

  constructor(protected http: HttpClient) {}

  create(collaborateur: ICollaborateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateur);
    return this.http
      .post<ICollaborateur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(collaborateur: ICollaborateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateur);
    return this.http
      .put<ICollaborateur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICollaborateur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICollaborateur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(collaborateur: ICollaborateur): ICollaborateur {
    const copy: ICollaborateur = Object.assign({}, collaborateur, {
      dateNaissance:
        collaborateur.dateNaissance && collaborateur.dateNaissance.isValid() ? collaborateur.dateNaissance.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? moment(res.body.dateNaissance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((collaborateur: ICollaborateur) => {
        collaborateur.dateNaissance = collaborateur.dateNaissance ? moment(collaborateur.dateNaissance) : undefined;
      });
    }
    return res;
  }
}
