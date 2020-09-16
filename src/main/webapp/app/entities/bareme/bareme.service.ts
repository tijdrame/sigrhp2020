import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBareme } from 'app/shared/model/bareme.model';

type EntityResponseType = HttpResponse<IBareme>;
type EntityArrayResponseType = HttpResponse<IBareme[]>;

@Injectable({ providedIn: 'root' })
export class BaremeService {
  public resourceUrl = SERVER_API_URL + 'api/baremes';

  constructor(protected http: HttpClient) {}

  create(bareme: IBareme): Observable<EntityResponseType> {
    return this.http.post<IBareme>(this.resourceUrl, bareme, { observe: 'response' });
  }

  update(bareme: IBareme): Observable<EntityResponseType> {
    return this.http.put<IBareme>(this.resourceUrl, bareme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBareme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBareme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
