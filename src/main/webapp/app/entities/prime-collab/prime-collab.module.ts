import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { PrimeCollabComponent } from './prime-collab.component';
import { PrimeCollabDetailComponent } from './prime-collab-detail.component';
import { PrimeCollabUpdateComponent } from './prime-collab-update.component';
import { PrimeCollabDeleteDialogComponent } from './prime-collab-delete-dialog.component';
import { primeCollabRoute } from './prime-collab.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(primeCollabRoute)],
  declarations: [PrimeCollabComponent, PrimeCollabDetailComponent, PrimeCollabUpdateComponent, PrimeCollabDeleteDialogComponent],
  entryComponents: [PrimeCollabDeleteDialogComponent],
})
export class SigrhpPrimeCollabModule {}
