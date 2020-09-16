import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { CollaborateurComponent } from './collaborateur.component';
import { CollaborateurDetailComponent } from './collaborateur-detail.component';
import { CollaborateurUpdateComponent } from './collaborateur-update.component';
import { CollaborateurDeleteDialogComponent } from './collaborateur-delete-dialog.component';
import { collaborateurRoute } from './collaborateur.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(collaborateurRoute)],
  declarations: [CollaborateurComponent, CollaborateurDetailComponent, CollaborateurUpdateComponent, CollaborateurDeleteDialogComponent],
  entryComponents: [CollaborateurDeleteDialogComponent],
})
export class SigrhpCollaborateurModule {}
