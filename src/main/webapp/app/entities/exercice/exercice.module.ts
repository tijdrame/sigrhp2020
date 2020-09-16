import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { ExerciceComponent } from './exercice.component';
import { ExerciceDetailComponent } from './exercice-detail.component';
import { ExerciceUpdateComponent } from './exercice-update.component';
import { ExerciceDeleteDialogComponent } from './exercice-delete-dialog.component';
import { exerciceRoute } from './exercice.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(exerciceRoute)],
  declarations: [ExerciceComponent, ExerciceDetailComponent, ExerciceUpdateComponent, ExerciceDeleteDialogComponent],
  entryComponents: [ExerciceDeleteDialogComponent],
})
export class SigrhpExerciceModule {}
