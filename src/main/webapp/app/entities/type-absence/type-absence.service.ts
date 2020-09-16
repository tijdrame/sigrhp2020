import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeAbsence } from 'app/shared/model/type-absence.model';

type EntityResponseType = HttpResponse<ITypeAbsence>;
type EntityArrayResponseType = HttpResponse<ITypeAbsence[]>;

@Injectable({ providedIn: 'root' })
export class TypeAbsenceService {
  public resourceUrl = SERVER_API_URL + 'api/type-absences';

  constructor(protected http: HttpClient) {}

  create(typeAbsence: ITypeAbsence): Observable<EntityResponseType> {
    return this.http.post<ITypeAbsence>(this.resourceUrl, typeAbsence, { observe: 'response' });
  }

  update(typeAbsence: ITypeAbsence): Observable<EntityResponseType> {
    return this.http.put<ITypeAbsence>(this.resourceUrl, typeAbsence, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeAbsence>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeAbsence[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
