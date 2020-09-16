import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDetailPret, DetailPret } from 'app/shared/model/detail-pret.model';
import { DetailPretService } from './detail-pret.service';
import { DetailPretComponent } from './detail-pret.component';
import { DetailPretDetailComponent } from './detail-pret-detail.component';
import { DetailPretUpdateComponent } from './detail-pret-update.component';

@Injectable({ providedIn: 'root' })
export class DetailPretResolve implements Resolve<IDetailPret> {
  constructor(private service: DetailPretService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailPret> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((detailPret: HttpResponse<DetailPret>) => {
          if (detailPret.body) {
            return of(detailPret.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetailPret());
  }
}

export const detailPretRoute: Routes = [
  {
    path: '',
    component: DetailPretComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.detailPret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailPretDetailComponent,
    resolve: {
      detailPret: DetailPretResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.detailPret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailPretUpdateComponent,
    resolve: {
      detailPret: DetailPretResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.detailPret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailPretUpdateComponent,
    resolve: {
      detailPret: DetailPretResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.detailPret.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
