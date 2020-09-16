import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMoisConcerne, MoisConcerne } from 'app/shared/model/mois-concerne.model';
import { MoisConcerneService } from './mois-concerne.service';
import { MoisConcerneComponent } from './mois-concerne.component';
import { MoisConcerneDetailComponent } from './mois-concerne-detail.component';
import { MoisConcerneUpdateComponent } from './mois-concerne-update.component';

@Injectable({ providedIn: 'root' })
export class MoisConcerneResolve implements Resolve<IMoisConcerne> {
  constructor(private service: MoisConcerneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoisConcerne> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((moisConcerne: HttpResponse<MoisConcerne>) => {
          if (moisConcerne.body) {
            return of(moisConcerne.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MoisConcerne());
  }
}

export const moisConcerneRoute: Routes = [
  {
    path: '',
    component: MoisConcerneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.moisConcerne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoisConcerneDetailComponent,
    resolve: {
      moisConcerne: MoisConcerneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.moisConcerne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoisConcerneUpdateComponent,
    resolve: {
      moisConcerne: MoisConcerneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.moisConcerne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoisConcerneUpdateComponent,
    resolve: {
      moisConcerne: MoisConcerneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.moisConcerne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
