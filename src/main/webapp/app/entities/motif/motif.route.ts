import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMotif, Motif } from 'app/shared/model/motif.model';
import { MotifService } from './motif.service';
import { MotifComponent } from './motif.component';
import { MotifDetailComponent } from './motif-detail.component';
import { MotifUpdateComponent } from './motif-update.component';

@Injectable({ providedIn: 'root' })
export class MotifResolve implements Resolve<IMotif> {
  constructor(private service: MotifService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMotif> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((motif: HttpResponse<Motif>) => {
          if (motif.body) {
            return of(motif.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Motif());
  }
}

export const motifRoute: Routes = [
  {
    path: '',
    component: MotifComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.motif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MotifDetailComponent,
    resolve: {
      motif: MotifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.motif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MotifUpdateComponent,
    resolve: {
      motif: MotifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.motif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MotifUpdateComponent,
    resolve: {
      motif: MotifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.motif.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
