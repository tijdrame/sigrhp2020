import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrimeCollab, PrimeCollab } from 'app/shared/model/prime-collab.model';
import { PrimeCollabService } from './prime-collab.service';
import { PrimeCollabComponent } from './prime-collab.component';
import { PrimeCollabDetailComponent } from './prime-collab-detail.component';
import { PrimeCollabUpdateComponent } from './prime-collab-update.component';

@Injectable({ providedIn: 'root' })
export class PrimeCollabResolve implements Resolve<IPrimeCollab> {
  constructor(private service: PrimeCollabService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrimeCollab> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((primeCollab: HttpResponse<PrimeCollab>) => {
          if (primeCollab.body) {
            return of(primeCollab.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PrimeCollab());
  }
}

export const primeCollabRoute: Routes = [
  {
    path: '',
    component: PrimeCollabComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.primeCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrimeCollabDetailComponent,
    resolve: {
      primeCollab: PrimeCollabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.primeCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrimeCollabUpdateComponent,
    resolve: {
      primeCollab: PrimeCollabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.primeCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrimeCollabUpdateComponent,
    resolve: {
      primeCollab: PrimeCollabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.primeCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
