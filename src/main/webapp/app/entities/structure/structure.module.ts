import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { StructureComponent } from './structure.component';
import { StructureDetailComponent } from './structure-detail.component';
import { StructureUpdateComponent } from './structure-update.component';
import { StructureDeleteDialogComponent } from './structure-delete-dialog.component';
import { structureRoute } from './structure.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(structureRoute)],
  declarations: [StructureComponent, StructureDetailComponent, StructureUpdateComponent, StructureDeleteDialogComponent],
  entryComponents: [StructureDeleteDialogComponent],
})
export class SigrhpStructureModule {}
