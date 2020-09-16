import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDetailPret } from 'app/shared/model/detail-pret.model';

type EntityResponseType = HttpResponse<IDetailPret>;
type EntityArrayResponseType = HttpResponse<IDetailPret[]>;

@Injectable({ providedIn: 'root' })
export class DetailPretService {
  public resourceUrl = SERVER_API_URL + 'api/detail-prets';

  constructor(protected http: HttpClient) {}

  create(detailPret: IDetailPret): Observable<EntityResponseType> {
    return this.http.post<IDetailPret>(this.resourceUrl, detailPret, { observe: 'response' });
  }

  update(detailPret: IDetailPret): Observable<EntityResponseType> {
    return this.http.put<IDetailPret>(this.resourceUrl, detailPret, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailPret>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailPret[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
