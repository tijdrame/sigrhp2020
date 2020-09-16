import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatutDemande } from 'app/shared/model/statut-demande.model';

type EntityResponseType = HttpResponse<IStatutDemande>;
type EntityArrayResponseType = HttpResponse<IStatutDemande[]>;

@Injectable({ providedIn: 'root' })
export class StatutDemandeService {
  public resourceUrl = SERVER_API_URL + 'api/statut-demandes';

  constructor(protected http: HttpClient) {}

  create(statutDemande: IStatutDemande): Observable<EntityResponseType> {
    return this.http.post<IStatutDemande>(this.resourceUrl, statutDemande, { observe: 'response' });
  }

  update(statutDemande: IStatutDemande): Observable<EntityResponseType> {
    return this.http.put<IStatutDemande>(this.resourceUrl, statutDemande, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatutDemande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatutDemande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
