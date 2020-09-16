import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISituationMatrimoniale, SituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';
import { SituationMatrimonialeComponent } from './situation-matrimoniale.component';
import { SituationMatrimonialeDetailComponent } from './situation-matrimoniale-detail.component';
import { SituationMatrimonialeUpdateComponent } from './situation-matrimoniale-update.component';

@Injectable({ providedIn: 'root' })
export class SituationMatrimonialeResolve implements Resolve<ISituationMatrimoniale> {
  constructor(private service: SituationMatrimonialeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISituationMatrimoniale> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((situationMatrimoniale: HttpResponse<SituationMatrimoniale>) => {
          if (situationMatrimoniale.body) {
            return of(situationMatrimoniale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SituationMatrimoniale());
  }
}

export const situationMatrimonialeRoute: Routes = [
  {
    path: '',
    component: SituationMatrimonialeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.situationMatrimoniale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SituationMatrimonialeDetailComponent,
    resolve: {
      situationMatrimoniale: SituationMatrimonialeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.situationMatrimoniale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SituationMatrimonialeUpdateComponent,
    resolve: {
      situationMatrimoniale: SituationMatrimonialeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.situationMatrimoniale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SituationMatrimonialeUpdateComponent,
    resolve: {
      situationMatrimoniale: SituationMatrimonialeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.situationMatrimoniale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
