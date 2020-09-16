import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrime, Prime } from 'app/shared/model/prime.model';
import { PrimeService } from './prime.service';
import { PrimeComponent } from './prime.component';
import { PrimeDetailComponent } from './prime-detail.component';
import { PrimeUpdateComponent } from './prime-update.component';

@Injectable({ providedIn: 'root' })
export class PrimeResolve implements Resolve<IPrime> {
  constructor(private service: PrimeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prime: HttpResponse<Prime>) => {
          if (prime.body) {
            return of(prime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Prime());
  }
}

export const primeRoute: Routes = [
  {
    path: '',
    component: PrimeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.prime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrimeDetailComponent,
    resolve: {
      prime: PrimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.prime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrimeUpdateComponent,
    resolve: {
      prime: PrimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.prime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrimeUpdateComponent,
    resolve: {
      prime: PrimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.prime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
