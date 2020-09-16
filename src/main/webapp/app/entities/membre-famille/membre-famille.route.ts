import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMembreFamille, MembreFamille } from 'app/shared/model/membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';
import { MembreFamilleComponent } from './membre-famille.component';
import { MembreFamilleDetailComponent } from './membre-famille-detail.component';
import { MembreFamilleUpdateComponent } from './membre-famille-update.component';

@Injectable({ providedIn: 'root' })
export class MembreFamilleResolve implements Resolve<IMembreFamille> {
  constructor(private service: MembreFamilleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMembreFamille> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((membreFamille: HttpResponse<MembreFamille>) => {
          if (membreFamille.body) {
            return of(membreFamille.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MembreFamille());
  }
}

export const membreFamilleRoute: Routes = [
  {
    path: '',
    component: MembreFamilleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.membreFamille.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MembreFamilleDetailComponent,
    resolve: {
      membreFamille: MembreFamilleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.membreFamille.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MembreFamilleUpdateComponent,
    resolve: {
      membreFamille: MembreFamilleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.membreFamille.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MembreFamilleUpdateComponent,
    resolve: {
      membreFamille: MembreFamilleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.membreFamille.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
