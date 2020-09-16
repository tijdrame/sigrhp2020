import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { DetailPretComponent } from './detail-pret.component';
import { DetailPretDetailComponent } from './detail-pret-detail.component';
import { DetailPretUpdateComponent } from './detail-pret-update.component';
import { DetailPretDeleteDialogComponent } from './detail-pret-delete-dialog.component';
import { detailPretRoute } from './detail-pret.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(detailPretRoute)],
  declarations: [DetailPretComponent, DetailPretDetailComponent, DetailPretUpdateComponent, DetailPretDeleteDialogComponent],
  entryComponents: [DetailPretDeleteDialogComponent],
})
export class SigrhpDetailPretModule {}
