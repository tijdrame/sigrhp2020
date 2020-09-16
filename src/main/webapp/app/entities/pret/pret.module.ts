import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { PretComponent } from './pret.component';
import { PretDetailComponent } from './pret-detail.component';
import { PretUpdateComponent } from './pret-update.component';
import { PretDeleteDialogComponent } from './pret-delete-dialog.component';
import { pretRoute } from './pret.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(pretRoute)],
  declarations: [PretComponent, PretDetailComponent, PretUpdateComponent, PretDeleteDialogComponent],
  entryComponents: [PretDeleteDialogComponent],
})
export class SigrhpPretModule {}
