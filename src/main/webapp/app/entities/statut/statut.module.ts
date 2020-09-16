import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { StatutComponent } from './statut.component';
import { StatutDetailComponent } from './statut-detail.component';
import { StatutUpdateComponent } from './statut-update.component';
import { StatutDeleteDialogComponent } from './statut-delete-dialog.component';
import { statutRoute } from './statut.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(statutRoute)],
  declarations: [StatutComponent, StatutDetailComponent, StatutUpdateComponent, StatutDeleteDialogComponent],
  entryComponents: [StatutDeleteDialogComponent],
})
export class SigrhpStatutModule {}
