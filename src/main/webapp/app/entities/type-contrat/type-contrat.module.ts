import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { TypeContratComponent } from './type-contrat.component';
import { TypeContratDetailComponent } from './type-contrat-detail.component';
import { TypeContratUpdateComponent } from './type-contrat-update.component';
import { TypeContratDeleteDialogComponent } from './type-contrat-delete-dialog.component';
import { typeContratRoute } from './type-contrat.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(typeContratRoute)],
  declarations: [TypeContratComponent, TypeContratDetailComponent, TypeContratUpdateComponent, TypeContratDeleteDialogComponent],
  entryComponents: [TypeContratDeleteDialogComponent],
})
export class SigrhpTypeContratModule {}
