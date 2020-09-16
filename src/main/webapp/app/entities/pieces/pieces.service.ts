import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPieces } from 'app/shared/model/pieces.model';

type EntityResponseType = HttpResponse<IPieces>;
type EntityArrayResponseType = HttpResponse<IPieces[]>;

@Injectable({ providedIn: 'root' })
export class PiecesService {
  public resourceUrl = SERVER_API_URL + 'api/pieces';

  constructor(protected http: HttpClient) {}

  create(pieces: IPieces): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pieces);
    return this.http
      .post<IPieces>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pieces: IPieces): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pieces);
    return this.http
      .put<IPieces>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPieces>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPieces[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pieces: IPieces): IPieces {
    const copy: IPieces = Object.assign({}, pieces, {
      dateCreation: pieces.dateCreation && pieces.dateCreation.isValid() ? pieces.dateCreation.format(DATE_FORMAT) : undefined,
      dateExpiration: pieces.dateExpiration && pieces.dateExpiration.isValid() ? pieces.dateExpiration.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
      res.body.dateExpiration = res.body.dateExpiration ? moment(res.body.dateExpiration) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pieces: IPieces) => {
        pieces.dateCreation = pieces.dateCreation ? moment(pieces.dateCreation) : undefined;
        pieces.dateExpiration = pieces.dateExpiration ? moment(pieces.dateExpiration) : undefined;
      });
    }
    return res;
  }
}
