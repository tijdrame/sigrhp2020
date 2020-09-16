import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { AvantageComponent } from './avantage.component';
import { AvantageDetailComponent } from './avantage-detail.component';
import { AvantageUpdateComponent } from './avantage-update.component';
import { AvantageDeleteDialogComponent } from './avantage-delete-dialog.component';
import { avantageRoute } from './avantage.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(avantageRoute)],
  declarations: [AvantageComponent, AvantageDetailComponent, AvantageUpdateComponent, AvantageDeleteDialogComponent],
  entryComponents: [AvantageDeleteDialogComponent],
})
export class SigrhpAvantageModule {}
