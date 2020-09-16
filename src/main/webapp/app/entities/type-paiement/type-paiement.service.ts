import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypePaiement } from 'app/shared/model/type-paiement.model';

type EntityResponseType = HttpResponse<ITypePaiement>;
type EntityArrayResponseType = HttpResponse<ITypePaiement[]>;

@Injectable({ providedIn: 'root' })
export class TypePaiementService {
  public resourceUrl = SERVER_API_URL + 'api/type-paiements';

  constructor(protected http: HttpClient) {}

  create(typePaiement: ITypePaiement): Observable<EntityResponseType> {
    return this.http.post<ITypePaiement>(this.resourceUrl, typePaiement, { observe: 'response' });
  }

  update(typePaiement: ITypePaiement): Observable<EntityResponseType> {
    return this.http.put<ITypePaiement>(this.resourceUrl, typePaiement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypePaiement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypePaiement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
