import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStructure, Structure } from 'app/shared/model/structure.model';
import { StructureService } from './structure.service';
import { StructureComponent } from './structure.component';
import { StructureDetailComponent } from './structure-detail.component';
import { StructureUpdateComponent } from './structure-update.component';

@Injectable({ providedIn: 'root' })
export class StructureResolve implements Resolve<IStructure> {
  constructor(private service: StructureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStructure> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((structure: HttpResponse<Structure>) => {
          if (structure.body) {
            return of(structure.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Structure());
  }
}

export const structureRoute: Routes = [
  {
    path: '',
    component: StructureComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigrhpApp.structure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StructureDetailComponent,
    resolve: {
      structure: StructureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.structure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StructureUpdateComponent,
    resolve: {
      structure: StructureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.structure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StructureUpdateComponent,
    resolve: {
      structure: StructureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigrhpApp.structure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
