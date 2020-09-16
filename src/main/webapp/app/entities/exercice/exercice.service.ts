import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExercice } from 'app/shared/model/exercice.model';

type EntityResponseType = HttpResponse<IExercice>;
type EntityArrayResponseType = HttpResponse<IExercice[]>;

@Injectable({ providedIn: 'root' })
export class ExerciceService {
  public resourceUrl = SERVER_API_URL + 'api/exercices';

  constructor(protected http: HttpClient) {}

  create(exercice: IExercice): Observable<EntityResponseType> {
    return this.http.post<IExercice>(this.resourceUrl, exercice, { observe: 'response' });
  }

  update(exercice: IExercice): Observable<EntityResponseType> {
    return this.http.put<IExercice>(this.resourceUrl, exercice, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExercice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExercice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
