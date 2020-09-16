import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatut, Statut } from 'app/shared/model/statut.model';
import { StatutService } from './statut.service';
import { StatutComponent } from './statut.component';
import { StatutDetailComponent } from './statut-detail.component';
import { StatutUpdateComponent } from './statut-update.component';

@Injectable({ providedIn: 'root' })
export class StatutResolve implements Resolve<IStatut> {
  constructor(private service: StatutService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatut> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statut: HttpResponse<Statut>) => {
          if (statut.body) {
            return of(statut.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Statut());
  }
}

export const statutRoute: Routes = [
  {
    path: '',
    component: StatutComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.statut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatutDetailComponent,
    resolve: {
      statut: StatutResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.statut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatutUpdateComponent,
    resolve: {
      statut: StatutResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.statut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatutUpdateComponent,
    resolve: {
      statut: StatutResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.statut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
