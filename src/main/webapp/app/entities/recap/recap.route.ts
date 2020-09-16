import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecap, Recap } from 'app/shared/model/recap.model';
import { RecapService } from './recap.service';
import { RecapComponent } from './recap.component';
import { RecapDetailComponent } from './recap-detail.component';
import { RecapUpdateComponent } from './recap-update.component';

@Injectable({ providedIn: 'root' })
export class RecapResolve implements Resolve<IRecap> {
  constructor(private service: RecapService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recap: HttpResponse<Recap>) => {
          if (recap.body) {
            return of(recap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Recap());
  }
}

export const recapRoute: Routes = [
  {
    path: '',
    component: RecapComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.recap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecapDetailComponent,
    resolve: {
      recap: RecapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.recap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecapUpdateComponent,
    resolve: {
      recap: RecapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.recap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecapUpdateComponent,
    resolve: {
      recap: RecapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.recap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
