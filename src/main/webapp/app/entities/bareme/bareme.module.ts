import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { BaremeComponent } from './bareme.component';
import { BaremeDetailComponent } from './bareme-detail.component';
import { BaremeUpdateComponent } from './bareme-update.component';
import { BaremeDeleteDialogComponent } from './bareme-delete-dialog.component';
import { baremeRoute } from './bareme.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(baremeRoute)],
  declarations: [BaremeComponent, BaremeDetailComponent, BaremeUpdateComponent, BaremeDeleteDialogComponent],
  entryComponents: [BaremeDeleteDialogComponent],
})
export class SigrhpBaremeModule {}
