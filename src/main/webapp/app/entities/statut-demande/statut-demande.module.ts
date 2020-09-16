import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { StatutDemandeComponent } from './statut-demande.component';
import { StatutDemandeDetailComponent } from './statut-demande-detail.component';
import { StatutDemandeUpdateComponent } from './statut-demande-update.component';
import { StatutDemandeDeleteDialogComponent } from './statut-demande-delete-dialog.component';
import { statutDemandeRoute } from './statut-demande.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(statutDemandeRoute)],
  declarations: [StatutDemandeComponent, StatutDemandeDetailComponent, StatutDemandeUpdateComponent, StatutDemandeDeleteDialogComponent],
  entryComponents: [StatutDemandeDeleteDialogComponent],
})
export class SigrhpStatutDemandeModule {}
