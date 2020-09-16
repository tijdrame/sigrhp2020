import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFonction, Fonction } from 'app/shared/model/fonction.model';
import { FonctionService } from './fonction.service';
import { FonctionComponent } from './fonction.component';
import { FonctionDetailComponent } from './fonction-detail.component';
import { FonctionUpdateComponent } from './fonction-update.component';

@Injectable({ providedIn: 'root' })
export class FonctionResolve implements Resolve<IFonction> {
  constructor(private service: FonctionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFonction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fonction: HttpResponse<Fonction>) => {
          if (fonction.body) {
            return of(fonction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fonction());
  }
}

export const fonctionRoute: Routes = [
  {
    path: '',
    component: FonctionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.fonction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FonctionDetailComponent,
    resolve: {
      fonction: FonctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.fonction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FonctionUpdateComponent,
    resolve: {
      fonction: FonctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.fonction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FonctionUpdateComponent,
    resolve: {
      fonction: FonctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.fonction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
