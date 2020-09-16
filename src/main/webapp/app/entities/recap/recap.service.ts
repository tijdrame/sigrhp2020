import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecap } from 'app/shared/model/recap.model';

type EntityResponseType = HttpResponse<IRecap>;
type EntityArrayResponseType = HttpResponse<IRecap[]>;

@Injectable({ providedIn: 'root' })
export class RecapService {
  public resourceUrl = SERVER_API_URL + 'api/recaps';

  constructor(protected http: HttpClient) {}

  create(recap: IRecap): Observable<EntityResponseType> {
    return this.http.post<IRecap>(this.resourceUrl, recap, { observe: 'response' });
  }

  update(recap: IRecap): Observable<EntityResponseType> {
    return this.http.put<IRecap>(this.resourceUrl, recap, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
