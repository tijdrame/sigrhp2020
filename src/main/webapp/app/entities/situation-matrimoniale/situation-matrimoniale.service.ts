import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';

type EntityResponseType = HttpResponse<ISituationMatrimoniale>;
type EntityArrayResponseType = HttpResponse<ISituationMatrimoniale[]>;

@Injectable({ providedIn: 'root' })
export class SituationMatrimonialeService {
  public resourceUrl = SERVER_API_URL + 'api/situation-matrimoniales';

  constructor(protected http: HttpClient) {}

  create(situationMatrimoniale: ISituationMatrimoniale): Observable<EntityResponseType> {
    return this.http.post<ISituationMatrimoniale>(this.resourceUrl, situationMatrimoniale, { observe: 'response' });
  }

  update(situationMatrimoniale: ISituationMatrimoniale): Observable<EntityResponseType> {
    return this.http.put<ISituationMatrimoniale>(this.resourceUrl, situationMatrimoniale, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISituationMatrimoniale>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISituationMatrimoniale[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
