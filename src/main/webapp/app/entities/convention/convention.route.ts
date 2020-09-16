import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConvention, Convention } from 'app/shared/model/convention.model';
import { ConventionService } from './convention.service';
import { ConventionComponent } from './convention.component';
import { ConventionDetailComponent } from './convention-detail.component';
import { ConventionUpdateComponent } from './convention-update.component';

@Injectable({ providedIn: 'root' })
export class ConventionResolve implements Resolve<IConvention> {
  constructor(private service: ConventionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConvention> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((convention: HttpResponse<Convention>) => {
          if (convention.body) {
            return of(convention.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Convention());
  }
}

export const conventionRoute: Routes = [
  {
    path: '',
    component: ConventionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.convention.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConventionDetailComponent,
    resolve: {
      convention: ConventionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.convention.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConventionUpdateComponent,
    resolve: {
      convention: ConventionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.convention.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConventionUpdateComponent,
    resolve: {
      convention: ConventionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.convention.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
