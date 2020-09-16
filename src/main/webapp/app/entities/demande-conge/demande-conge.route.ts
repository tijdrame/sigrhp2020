import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDemandeConge, DemandeConge } from 'app/shared/model/demande-conge.model';
import { DemandeCongeService } from './demande-conge.service';
import { DemandeCongeComponent } from './demande-conge.component';
import { DemandeCongeDetailComponent } from './demande-conge-detail.component';
import { DemandeCongeUpdateComponent } from './demande-conge-update.component';

@Injectable({ providedIn: 'root' })
export class DemandeCongeResolve implements Resolve<IDemandeConge> {
  constructor(private service: DemandeCongeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeConge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((demandeConge: HttpResponse<DemandeConge>) => {
          if (demandeConge.body) {
            return of(demandeConge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandeConge());
  }
}

export const demandeCongeRoute: Routes = [
  {
    path: '',
    component: DemandeCongeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.demandeConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeCongeDetailComponent,
    resolve: {
      demandeConge: DemandeCongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.demandeConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeCongeUpdateComponent,
    resolve: {
      demandeConge: DemandeCongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.demandeConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeCongeUpdateComponent,
    resolve: {
      demandeConge: DemandeCongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.demandeConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
