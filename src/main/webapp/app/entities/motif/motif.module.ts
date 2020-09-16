import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { MotifComponent } from './motif.component';
import { MotifDetailComponent } from './motif-detail.component';
import { MotifUpdateComponent } from './motif-update.component';
import { MotifDeleteDialogComponent } from './motif-delete-dialog.component';
import { motifRoute } from './motif.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(motifRoute)],
  declarations: [MotifComponent, MotifDetailComponent, MotifUpdateComponent, MotifDeleteDialogComponent],
  entryComponents: [MotifDeleteDialogComponent],
})
export class SigrhpMotifModule {}
