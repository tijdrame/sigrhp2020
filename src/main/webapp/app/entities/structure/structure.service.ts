import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStructure } from 'app/shared/model/structure.model';

type EntityResponseType = HttpResponse<IStructure>;
type EntityArrayResponseType = HttpResponse<IStructure[]>;

@Injectable({ providedIn: 'root' })
export class StructureService {
  public resourceUrl = SERVER_API_URL + 'api/structures';

  constructor(protected http: HttpClient) {}

  create(structure: IStructure): Observable<EntityResponseType> {
    return this.http.post<IStructure>(this.resourceUrl, structure, { observe: 'response' });
  }

  update(structure: IStructure): Observable<EntityResponseType> {
    return this.http.put<IStructure>(this.resourceUrl, structure, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStructure>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStructure[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
