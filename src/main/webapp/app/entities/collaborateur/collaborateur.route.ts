import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICollaborateur, Collaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from './collaborateur.service';
import { CollaborateurComponent } from './collaborateur.component';
import { CollaborateurDetailComponent } from './collaborateur-detail.component';
import { CollaborateurUpdateComponent } from './collaborateur-update.component';

@Injectable({ providedIn: 'root' })
export class CollaborateurResolve implements Resolve<ICollaborateur> {
  constructor(private service: CollaborateurService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollaborateur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collaborateur: HttpResponse<Collaborateur>) => {
          if (collaborateur.body) {
            return of(collaborateur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Collaborateur());
  }
}

export const collaborateurRoute: Routes = [
  {
    path: '',
    component: CollaborateurComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.collaborateur.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CollaborateurDetailComponent,
    resolve: {
      collaborateur: CollaborateurResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.collaborateur.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CollaborateurUpdateComponent,
    resolve: {
      collaborateur: CollaborateurResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.collaborateur.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CollaborateurUpdateComponent,
    resolve: {
      collaborateur: CollaborateurResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.collaborateur.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
