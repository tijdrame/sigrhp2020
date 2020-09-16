import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRemboursement, Remboursement } from 'app/shared/model/remboursement.model';
import { RemboursementService } from './remboursement.service';
import { RemboursementComponent } from './remboursement.component';
import { RemboursementDetailComponent } from './remboursement-detail.component';
import { RemboursementUpdateComponent } from './remboursement-update.component';

@Injectable({ providedIn: 'root' })
export class RemboursementResolve implements Resolve<IRemboursement> {
  constructor(private service: RemboursementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRemboursement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((remboursement: HttpResponse<Remboursement>) => {
          if (remboursement.body) {
            return of(remboursement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Remboursement());
  }
}

export const remboursementRoute: Routes = [
  {
    path: '',
    component: RemboursementComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.remboursement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RemboursementDetailComponent,
    resolve: {
      remboursement: RemboursementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.remboursement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RemboursementUpdateComponent,
    resolve: {
      remboursement: RemboursementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.remboursement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RemboursementUpdateComponent,
    resolve: {
      remboursement: RemboursementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.remboursement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
