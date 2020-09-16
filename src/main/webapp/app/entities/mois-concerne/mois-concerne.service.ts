import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMoisConcerne } from 'app/shared/model/mois-concerne.model';

type EntityResponseType = HttpResponse<IMoisConcerne>;
type EntityArrayResponseType = HttpResponse<IMoisConcerne[]>;

@Injectable({ providedIn: 'root' })
export class MoisConcerneService {
  public resourceUrl = SERVER_API_URL + 'api/mois-concernes';

  constructor(protected http: HttpClient) {}

  create(moisConcerne: IMoisConcerne): Observable<EntityResponseType> {
    return this.http.post<IMoisConcerne>(this.resourceUrl, moisConcerne, { observe: 'response' });
  }

  update(moisConcerne: IMoisConcerne): Observable<EntityResponseType> {
    return this.http.put<IMoisConcerne>(this.resourceUrl, moisConcerne, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoisConcerne>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoisConcerne[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
