import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { NationaliteComponent } from './nationalite.component';
import { NationaliteDetailComponent } from './nationalite-detail.component';
import { NationaliteUpdateComponent } from './nationalite-update.component';
import { NationaliteDeleteDialogComponent } from './nationalite-delete-dialog.component';
import { nationaliteRoute } from './nationalite.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(nationaliteRoute)],
  declarations: [NationaliteComponent, NationaliteDetailComponent, NationaliteUpdateComponent, NationaliteDeleteDialogComponent],
  entryComponents: [NationaliteDeleteDialogComponent],
})
export class SigrhpNationaliteModule {}
