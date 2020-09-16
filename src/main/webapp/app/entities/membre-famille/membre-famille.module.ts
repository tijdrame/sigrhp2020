import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { MembreFamilleComponent } from './membre-famille.component';
import { MembreFamilleDetailComponent } from './membre-famille-detail.component';
import { MembreFamilleUpdateComponent } from './membre-famille-update.component';
import { MembreFamilleDeleteDialogComponent } from './membre-famille-delete-dialog.component';
import { membreFamilleRoute } from './membre-famille.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(membreFamilleRoute)],
  declarations: [MembreFamilleComponent, MembreFamilleDetailComponent, MembreFamilleUpdateComponent, MembreFamilleDeleteDialogComponent],
  entryComponents: [MembreFamilleDeleteDialogComponent],
})
export class SigrhpMembreFamilleModule {}
