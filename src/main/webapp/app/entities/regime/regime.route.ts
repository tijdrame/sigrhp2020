import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRegime, Regime } from 'app/shared/model/regime.model';
import { RegimeService } from './regime.service';
import { RegimeComponent } from './regime.component';
import { RegimeDetailComponent } from './regime-detail.component';
import { RegimeUpdateComponent } from './regime-update.component';

@Injectable({ providedIn: 'root' })
export class RegimeResolve implements Resolve<IRegime> {
  constructor(private service: RegimeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((regime: HttpResponse<Regime>) => {
          if (regime.body) {
            return of(regime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Regime());
  }
}

export const regimeRoute: Routes = [
  {
    path: '',
    component: RegimeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.regime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RegimeDetailComponent,
    resolve: {
      regime: RegimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.regime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegimeUpdateComponent,
    resolve: {
      regime: RegimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.regime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RegimeUpdateComponent,
    resolve: {
      regime: RegimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.regime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
