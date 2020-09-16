import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMotif } from 'app/shared/model/motif.model';

type EntityResponseType = HttpResponse<IMotif>;
type EntityArrayResponseType = HttpResponse<IMotif[]>;

@Injectable({ providedIn: 'root' })
export class MotifService {
  public resourceUrl = SERVER_API_URL + 'api/motifs';

  constructor(protected http: HttpClient) {}

  create(motif: IMotif): Observable<EntityResponseType> {
    return this.http.post<IMotif>(this.resourceUrl, motif, { observe: 'response' });
  }

  update(motif: IMotif): Observable<EntityResponseType> {
    return this.http.put<IMotif>(this.resourceUrl, motif, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMotif>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMotif[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
