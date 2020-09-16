import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeRelation } from 'app/shared/model/type-relation.model';

type EntityResponseType = HttpResponse<ITypeRelation>;
type EntityArrayResponseType = HttpResponse<ITypeRelation[]>;

@Injectable({ providedIn: 'root' })
export class TypeRelationService {
  public resourceUrl = SERVER_API_URL + 'api/type-relations';

  constructor(protected http: HttpClient) {}

  create(typeRelation: ITypeRelation): Observable<EntityResponseType> {
    return this.http.post<ITypeRelation>(this.resourceUrl, typeRelation, { observe: 'response' });
  }

  update(typeRelation: ITypeRelation): Observable<EntityResponseType> {
    return this.http.put<ITypeRelation>(this.resourceUrl, typeRelation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
