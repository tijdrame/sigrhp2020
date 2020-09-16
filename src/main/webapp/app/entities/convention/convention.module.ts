import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { ConventionComponent } from './convention.component';
import { ConventionDetailComponent } from './convention-detail.component';
import { ConventionUpdateComponent } from './convention-update.component';
import { ConventionDeleteDialogComponent } from './convention-delete-dialog.component';
import { conventionRoute } from './convention.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(conventionRoute)],
  declarations: [ConventionComponent, ConventionDetailComponent, ConventionUpdateComponent, ConventionDeleteDialogComponent],
  entryComponents: [ConventionDeleteDialogComponent],
})
export class SigrhpConventionModule {}
