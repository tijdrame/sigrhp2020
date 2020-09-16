import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISexe, Sexe } from 'app/shared/model/sexe.model';
import { SexeService } from './sexe.service';
import { SexeComponent } from './sexe.component';
import { SexeDetailComponent } from './sexe-detail.component';
import { SexeUpdateComponent } from './sexe-update.component';

@Injectable({ providedIn: 'root' })
export class SexeResolve implements Resolve<ISexe> {
  constructor(private service: SexeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISexe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sexe: HttpResponse<Sexe>) => {
          if (sexe.body) {
            return of(sexe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sexe());
  }
}

export const sexeRoute: Routes = [
  {
    path: '',
    component: SexeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.sexe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SexeDetailComponent,
    resolve: {
      sexe: SexeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.sexe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SexeUpdateComponent,
    resolve: {
      sexe: SexeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.sexe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SexeUpdateComponent,
    resolve: {
      sexe: SexeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.sexe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
