import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegime } from 'app/shared/model/regime.model';

type EntityResponseType = HttpResponse<IRegime>;
type EntityArrayResponseType = HttpResponse<IRegime[]>;

@Injectable({ providedIn: 'root' })
export class RegimeService {
  public resourceUrl = SERVER_API_URL + 'api/regimes';

  constructor(protected http: HttpClient) {}

  create(regime: IRegime): Observable<EntityResponseType> {
    return this.http.post<IRegime>(this.resourceUrl, regime, { observe: 'response' });
  }

  update(regime: IRegime): Observable<EntityResponseType> {
    return this.http.put<IRegime>(this.resourceUrl, regime, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegime>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegime[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
