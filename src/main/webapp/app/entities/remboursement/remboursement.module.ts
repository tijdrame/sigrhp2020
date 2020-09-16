import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { RemboursementComponent } from './remboursement.component';
import { RemboursementDetailComponent } from './remboursement-detail.component';
import { RemboursementUpdateComponent } from './remboursement-update.component';
import { RemboursementDeleteDialogComponent } from './remboursement-delete-dialog.component';
import { remboursementRoute } from './remboursement.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(remboursementRoute)],
  declarations: [RemboursementComponent, RemboursementDetailComponent, RemboursementUpdateComponent, RemboursementDeleteDialogComponent],
  entryComponents: [RemboursementDeleteDialogComponent],
})
export class SigrhpRemboursementModule {}
