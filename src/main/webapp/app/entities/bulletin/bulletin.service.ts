import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBulletin } from 'app/shared/model/bulletin.model';

type EntityResponseType = HttpResponse<IBulletin>;
type EntityArrayResponseType = HttpResponse<IBulletin[]>;

@Injectable({ providedIn: 'root' })
export class BulletinService {
  public resourceUrl = SERVER_API_URL + 'api/bulletins';

  constructor(protected http: HttpClient) {}

  create(bulletin: IBulletin): Observable<EntityResponseType> {
    return this.http.post<IBulletin>(this.resourceUrl, bulletin, { observe: 'response' });
  }

  update(bulletin: IBulletin): Observable<EntityResponseType> {
    return this.http.put<IBulletin>(this.resourceUrl, bulletin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBulletin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBulletin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
