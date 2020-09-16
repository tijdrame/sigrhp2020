import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeContrat, TypeContrat } from 'app/shared/model/type-contrat.model';
import { TypeContratService } from './type-contrat.service';
import { TypeContratComponent } from './type-contrat.component';
import { TypeContratDetailComponent } from './type-contrat-detail.component';
import { TypeContratUpdateComponent } from './type-contrat-update.component';

@Injectable({ providedIn: 'root' })
export class TypeContratResolve implements Resolve<ITypeContrat> {
  constructor(private service: TypeContratService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeContrat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeContrat: HttpResponse<TypeContrat>) => {
          if (typeContrat.body) {
            return of(typeContrat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeContrat());
  }
}

export const typeContratRoute: Routes = [
  {
    path: '',
    component: TypeContratComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.typeContrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeContratDetailComponent,
    resolve: {
      typeContrat: TypeContratResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typeContrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeContratUpdateComponent,
    resolve: {
      typeContrat: TypeContratResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typeContrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeContratUpdateComponent,
    resolve: {
      typeContrat: TypeContratResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typeContrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
