import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';

type EntityResponseType = HttpResponse<ITypeContrat>;
type EntityArrayResponseType = HttpResponse<ITypeContrat[]>;

@Injectable({ providedIn: 'root' })
export class TypeContratService {
  public resourceUrl = SERVER_API_URL + 'api/type-contrats';

  constructor(protected http: HttpClient) {}

  create(typeContrat: ITypeContrat): Observable<EntityResponseType> {
    return this.http.post<ITypeContrat>(this.resourceUrl, typeContrat, { observe: 'response' });
  }

  update(typeContrat: ITypeContrat): Observable<EntityResponseType> {
    return this.http.put<ITypeContrat>(this.resourceUrl, typeContrat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeContrat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeContrat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
