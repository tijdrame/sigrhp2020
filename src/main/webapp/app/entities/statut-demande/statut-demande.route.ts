import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatutDemande, StatutDemande } from 'app/shared/model/statut-demande.model';
import { StatutDemandeService } from './statut-demande.service';
import { StatutDemandeComponent } from './statut-demande.component';
import { StatutDemandeDetailComponent } from './statut-demande-detail.component';
import { StatutDemandeUpdateComponent } from './statut-demande-update.component';

@Injectable({ providedIn: 'root' })
export class StatutDemandeResolve implements Resolve<IStatutDemande> {
  constructor(private service: StatutDemandeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatutDemande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statutDemande: HttpResponse<StatutDemande>) => {
          if (statutDemande.body) {
            return of(statutDemande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatutDemande());
  }
}

export const statutDemandeRoute: Routes = [
  {
    path: '',
    component: StatutDemandeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.statutDemande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatutDemandeDetailComponent,
    resolve: {
      statutDemande: StatutDemandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.statutDemande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatutDemandeUpdateComponent,
    resolve: {
      statutDemande: StatutDemandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.statutDemande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatutDemandeUpdateComponent,
    resolve: {
      statutDemande: StatutDemandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.statutDemande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
