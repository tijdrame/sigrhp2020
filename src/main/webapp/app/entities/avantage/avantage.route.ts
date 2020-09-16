import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvantage, Avantage } from 'app/shared/model/avantage.model';
import { AvantageService } from './avantage.service';
import { AvantageComponent } from './avantage.component';
import { AvantageDetailComponent } from './avantage-detail.component';
import { AvantageUpdateComponent } from './avantage-update.component';

@Injectable({ providedIn: 'root' })
export class AvantageResolve implements Resolve<IAvantage> {
  constructor(private service: AvantageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvantage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avantage: HttpResponse<Avantage>) => {
          if (avantage.body) {
            return of(avantage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Avantage());
  }
}

export const avantageRoute: Routes = [
  {
    path: '',
    component: AvantageComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.avantage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvantageDetailComponent,
    resolve: {
      avantage: AvantageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.avantage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvantageUpdateComponent,
    resolve: {
      avantage: AvantageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.avantage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvantageUpdateComponent,
    resolve: {
      avantage: AvantageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.avantage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
