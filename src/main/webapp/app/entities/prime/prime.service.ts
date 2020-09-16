import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrime } from 'app/shared/model/prime.model';

type EntityResponseType = HttpResponse<IPrime>;
type EntityArrayResponseType = HttpResponse<IPrime[]>;

@Injectable({ providedIn: 'root' })
export class PrimeService {
  public resourceUrl = SERVER_API_URL + 'api/primes';

  constructor(protected http: HttpClient) {}

  create(prime: IPrime): Observable<EntityResponseType> {
    return this.http.post<IPrime>(this.resourceUrl, prime, { observe: 'response' });
  }

  update(prime: IPrime): Observable<EntityResponseType> {
    return this.http.put<IPrime>(this.resourceUrl, prime, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrime>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrime[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
