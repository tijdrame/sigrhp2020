import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBulletin, Bulletin } from 'app/shared/model/bulletin.model';
import { BulletinService } from './bulletin.service';
import { BulletinComponent } from './bulletin.component';
import { BulletinDetailComponent } from './bulletin-detail.component';
import { BulletinUpdateComponent } from './bulletin-update.component';

@Injectable({ providedIn: 'root' })
export class BulletinResolve implements Resolve<IBulletin> {
  constructor(private service: BulletinService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBulletin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bulletin: HttpResponse<Bulletin>) => {
          if (bulletin.body) {
            return of(bulletin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bulletin());
  }
}

export const bulletinRoute: Routes = [
  {
    path: '',
    component: BulletinComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.bulletin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BulletinDetailComponent,
    resolve: {
      bulletin: BulletinResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.bulletin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BulletinUpdateComponent,
    resolve: {
      bulletin: BulletinResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.bulletin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BulletinUpdateComponent,
    resolve: {
      bulletin: BulletinResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.bulletin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
