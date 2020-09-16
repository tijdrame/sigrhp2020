import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { TypePaiementComponent } from './type-paiement.component';
import { TypePaiementDetailComponent } from './type-paiement-detail.component';
import { TypePaiementUpdateComponent } from './type-paiement-update.component';
import { TypePaiementDeleteDialogComponent } from './type-paiement-delete-dialog.component';
import { typePaiementRoute } from './type-paiement.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(typePaiementRoute)],
  declarations: [TypePaiementComponent, TypePaiementDetailComponent, TypePaiementUpdateComponent, TypePaiementDeleteDialogComponent],
  entryComponents: [TypePaiementDeleteDialogComponent],
})
export class SigrhpTypePaiementModule {}
