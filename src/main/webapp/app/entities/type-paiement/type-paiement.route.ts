import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePaiement, TypePaiement } from 'app/shared/model/type-paiement.model';
import { TypePaiementService } from './type-paiement.service';
import { TypePaiementComponent } from './type-paiement.component';
import { TypePaiementDetailComponent } from './type-paiement-detail.component';
import { TypePaiementUpdateComponent } from './type-paiement-update.component';

@Injectable({ providedIn: 'root' })
export class TypePaiementResolve implements Resolve<ITypePaiement> {
  constructor(private service: TypePaiementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePaiement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePaiement: HttpResponse<TypePaiement>) => {
          if (typePaiement.body) {
            return of(typePaiement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePaiement());
  }
}

export const typePaiementRoute: Routes = [
  {
    path: '',
    component: TypePaiementComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.typePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypePaiementDetailComponent,
    resolve: {
      typePaiement: TypePaiementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypePaiementUpdateComponent,
    resolve: {
      typePaiement: TypePaiementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypePaiementUpdateComponent,
    resolve: {
      typePaiement: TypePaiementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typePaiement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
