import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { TypeAbsenceComponent } from './type-absence.component';
import { TypeAbsenceDetailComponent } from './type-absence-detail.component';
import { TypeAbsenceUpdateComponent } from './type-absence-update.component';
import { TypeAbsenceDeleteDialogComponent } from './type-absence-delete-dialog.component';
import { typeAbsenceRoute } from './type-absence.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(typeAbsenceRoute)],
  declarations: [TypeAbsenceComponent, TypeAbsenceDetailComponent, TypeAbsenceUpdateComponent, TypeAbsenceDeleteDialogComponent],
  entryComponents: [TypeAbsenceDeleteDialogComponent],
})
export class SigrhpTypeAbsenceModule {}
