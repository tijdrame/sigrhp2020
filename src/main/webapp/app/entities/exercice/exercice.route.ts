import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExercice, Exercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from './exercice.service';
import { ExerciceComponent } from './exercice.component';
import { ExerciceDetailComponent } from './exercice-detail.component';
import { ExerciceUpdateComponent } from './exercice-update.component';

@Injectable({ providedIn: 'root' })
export class ExerciceResolve implements Resolve<IExercice> {
  constructor(private service: ExerciceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExercice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((exercice: HttpResponse<Exercice>) => {
          if (exercice.body) {
            return of(exercice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Exercice());
  }
}

export const exerciceRoute: Routes = [
  {
    path: '',
    component: ExerciceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.exercice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExerciceDetailComponent,
    resolve: {
      exercice: ExerciceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.exercice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExerciceUpdateComponent,
    resolve: {
      exercice: ExerciceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.exercice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExerciceUpdateComponent,
    resolve: {
      exercice: ExerciceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.exercice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
