import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { SexeComponent } from './sexe.component';
import { SexeDetailComponent } from './sexe-detail.component';
import { SexeUpdateComponent } from './sexe-update.component';
import { SexeDeleteDialogComponent } from './sexe-delete-dialog.component';
import { sexeRoute } from './sexe.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(sexeRoute)],
  declarations: [SexeComponent, SexeDetailComponent, SexeUpdateComponent, SexeDeleteDialogComponent],
  entryComponents: [SexeDeleteDialogComponent],
})
export class SigrhpSexeModule {}
