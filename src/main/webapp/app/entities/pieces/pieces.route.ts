import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPieces, Pieces } from 'app/shared/model/pieces.model';
import { PiecesService } from './pieces.service';
import { PiecesComponent } from './pieces.component';
import { PiecesDetailComponent } from './pieces-detail.component';
import { PiecesUpdateComponent } from './pieces-update.component';

@Injectable({ providedIn: 'root' })
export class PiecesResolve implements Resolve<IPieces> {
  constructor(private service: PiecesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPieces> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pieces: HttpResponse<Pieces>) => {
          if (pieces.body) {
            return of(pieces.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pieces());
  }
}

export const piecesRoute: Routes = [
  {
    path: '',
    component: PiecesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.pieces.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PiecesDetailComponent,
    resolve: {
      pieces: PiecesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.pieces.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PiecesUpdateComponent,
    resolve: {
      pieces: PiecesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.pieces.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PiecesUpdateComponent,
    resolve: {
      pieces: PiecesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.pieces.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
