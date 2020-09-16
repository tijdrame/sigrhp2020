import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvantageCollab, AvantageCollab } from 'app/shared/model/avantage-collab.model';
import { AvantageCollabService } from './avantage-collab.service';
import { AvantageCollabComponent } from './avantage-collab.component';
import { AvantageCollabDetailComponent } from './avantage-collab-detail.component';
import { AvantageCollabUpdateComponent } from './avantage-collab-update.component';

@Injectable({ providedIn: 'root' })
export class AvantageCollabResolve implements Resolve<IAvantageCollab> {
  constructor(private service: AvantageCollabService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvantageCollab> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avantageCollab: HttpResponse<AvantageCollab>) => {
          if (avantageCollab.body) {
            return of(avantageCollab.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvantageCollab());
  }
}

export const avantageCollabRoute: Routes = [
  {
    path: '',
    component: AvantageCollabComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.avantageCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvantageCollabDetailComponent,
    resolve: {
      avantageCollab: AvantageCollabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.avantageCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvantageCollabUpdateComponent,
    resolve: {
      avantageCollab: AvantageCollabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.avantageCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvantageCollabUpdateComponent,
    resolve: {
      avantageCollab: AvantageCollabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.avantageCollab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
