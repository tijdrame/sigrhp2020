import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvantageCollab } from 'app/shared/model/avantage-collab.model';

type EntityResponseType = HttpResponse<IAvantageCollab>;
type EntityArrayResponseType = HttpResponse<IAvantageCollab[]>;

@Injectable({ providedIn: 'root' })
export class AvantageCollabService {
  public resourceUrl = SERVER_API_URL + 'api/avantage-collabs';

  constructor(protected http: HttpClient) {}

  create(avantageCollab: IAvantageCollab): Observable<EntityResponseType> {
    return this.http.post<IAvantageCollab>(this.resourceUrl, avantageCollab, { observe: 'response' });
  }

  update(avantageCollab: IAvantageCollab): Observable<EntityResponseType> {
    return this.http.put<IAvantageCollab>(this.resourceUrl, avantageCollab, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvantageCollab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvantageCollab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
