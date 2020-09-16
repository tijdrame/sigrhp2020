import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { RecapComponent } from './recap.component';
import { RecapDetailComponent } from './recap-detail.component';
import { RecapUpdateComponent } from './recap-update.component';
import { RecapDeleteDialogComponent } from './recap-delete-dialog.component';
import { recapRoute } from './recap.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(recapRoute)],
  declarations: [RecapComponent, RecapDetailComponent, RecapUpdateComponent, RecapDeleteDialogComponent],
  entryComponents: [RecapDeleteDialogComponent],
})
export class SigrhpRecapModule {}
