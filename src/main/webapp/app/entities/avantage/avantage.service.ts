import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvantage } from 'app/shared/model/avantage.model';

type EntityResponseType = HttpResponse<IAvantage>;
type EntityArrayResponseType = HttpResponse<IAvantage[]>;

@Injectable({ providedIn: 'root' })
export class AvantageService {
  public resourceUrl = SERVER_API_URL + 'api/avantages';

  constructor(protected http: HttpClient) {}

  create(avantage: IAvantage): Observable<EntityResponseType> {
    return this.http.post<IAvantage>(this.resourceUrl, avantage, { observe: 'response' });
  }

  update(avantage: IAvantage): Observable<EntityResponseType> {
    return this.http.put<IAvantage>(this.resourceUrl, avantage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvantage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvantage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
