import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISexe } from 'app/shared/model/sexe.model';

type EntityResponseType = HttpResponse<ISexe>;
type EntityArrayResponseType = HttpResponse<ISexe[]>;

@Injectable({ providedIn: 'root' })
export class SexeService {
  public resourceUrl = SERVER_API_URL + 'api/sexes';

  constructor(protected http: HttpClient) {}

  create(sexe: ISexe): Observable<EntityResponseType> {
    return this.http.post<ISexe>(this.resourceUrl, sexe, { observe: 'response' });
  }

  update(sexe: ISexe): Observable<EntityResponseType> {
    return this.http.put<ISexe>(this.resourceUrl, sexe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISexe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISexe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
