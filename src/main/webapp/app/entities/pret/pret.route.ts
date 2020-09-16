import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPret, Pret } from 'app/shared/model/pret.model';
import { PretService } from './pret.service';
import { PretComponent } from './pret.component';
import { PretDetailComponent } from './pret-detail.component';
import { PretUpdateComponent } from './pret-update.component';

@Injectable({ providedIn: 'root' })
export class PretResolve implements Resolve<IPret> {
  constructor(private service: PretService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPret> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pret: HttpResponse<Pret>) => {
          if (pret.body) {
            return of(pret.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pret());
  }
}

export const pretRoute: Routes = [
  {
    path: '',
    component: PretComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.pret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PretDetailComponent,
    resolve: {
      pret: PretResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.pret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PretUpdateComponent,
    resolve: {
      pret: PretResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.pret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PretUpdateComponent,
    resolve: {
      pret: PretResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.pret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
