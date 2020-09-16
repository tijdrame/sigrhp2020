import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { SituationMatrimonialeComponent } from './situation-matrimoniale.component';
import { SituationMatrimonialeDetailComponent } from './situation-matrimoniale-detail.component';
import { SituationMatrimonialeUpdateComponent } from './situation-matrimoniale-update.component';
import { SituationMatrimonialeDeleteDialogComponent } from './situation-matrimoniale-delete-dialog.component';
import { situationMatrimonialeRoute } from './situation-matrimoniale.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(situationMatrimonialeRoute)],
  declarations: [
    SituationMatrimonialeComponent,
    SituationMatrimonialeDetailComponent,
    SituationMatrimonialeUpdateComponent,
    SituationMatrimonialeDeleteDialogComponent,
  ],
  entryComponents: [SituationMatrimonialeDeleteDialogComponent],
})
export class SigrhpSituationMatrimonialeModule {}
