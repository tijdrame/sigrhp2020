import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeRelation, TypeRelation } from 'app/shared/model/type-relation.model';
import { TypeRelationService } from './type-relation.service';
import { TypeRelationComponent } from './type-relation.component';
import { TypeRelationDetailComponent } from './type-relation-detail.component';
import { TypeRelationUpdateComponent } from './type-relation-update.component';

@Injectable({ providedIn: 'root' })
export class TypeRelationResolve implements Resolve<ITypeRelation> {
  constructor(private service: TypeRelationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeRelation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeRelation: HttpResponse<TypeRelation>) => {
          if (typeRelation.body) {
            return of(typeRelation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeRelation());
  }
}

export const typeRelationRoute: Routes = [
  {
    path: '',
    component: TypeRelationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.typeRelation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeRelationDetailComponent,
    resolve: {
      typeRelation: TypeRelationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typeRelation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeRelationUpdateComponent,
    resolve: {
      typeRelation: TypeRelationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typeRelation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeRelationUpdateComponent,
    resolve: {
      typeRelation: TypeRelationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.typeRelation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
