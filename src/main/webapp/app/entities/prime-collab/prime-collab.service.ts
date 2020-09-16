import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrimeCollab } from 'app/shared/model/prime-collab.model';

type EntityResponseType = HttpResponse<IPrimeCollab>;
type EntityArrayResponseType = HttpResponse<IPrimeCollab[]>;

@Injectable({ providedIn: 'root' })
export class PrimeCollabService {
  public resourceUrl = SERVER_API_URL + 'api/prime-collabs';

  constructor(protected http: HttpClient) {}

  create(primeCollab: IPrimeCollab): Observable<EntityResponseType> {
    return this.http.post<IPrimeCollab>(this.resourceUrl, primeCollab, { observe: 'response' });
  }

  update(primeCollab: IPrimeCollab): Observable<EntityResponseType> {
    return this.http.put<IPrimeCollab>(this.resourceUrl, primeCollab, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrimeCollab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrimeCollab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
