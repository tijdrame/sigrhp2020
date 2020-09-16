import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConvention } from 'app/shared/model/convention.model';

type EntityResponseType = HttpResponse<IConvention>;
type EntityArrayResponseType = HttpResponse<IConvention[]>;

@Injectable({ providedIn: 'root' })
export class ConventionService {
  public resourceUrl = SERVER_API_URL + 'api/conventions';

  constructor(protected http: HttpClient) {}

  create(convention: IConvention): Observable<EntityResponseType> {
    return this.http.post<IConvention>(this.resourceUrl, convention, { observe: 'response' });
  }

  update(convention: IConvention): Observable<EntityResponseType> {
    return this.http.put<IConvention>(this.resourceUrl, convention, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConvention>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConvention[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
