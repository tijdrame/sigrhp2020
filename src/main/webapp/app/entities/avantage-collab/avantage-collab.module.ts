import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { AvantageCollabComponent } from './avantage-collab.component';
import { AvantageCollabDetailComponent } from './avantage-collab-detail.component';
import { AvantageCollabUpdateComponent } from './avantage-collab-update.component';
import { AvantageCollabDeleteDialogComponent } from './avantage-collab-delete-dialog.component';
import { avantageCollabRoute } from './avantage-collab.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(avantageCollabRoute)],
  declarations: [
    AvantageCollabComponent,
    AvantageCollabDetailComponent,
    AvantageCollabUpdateComponent,
    AvantageCollabDeleteDialogComponent,
  ],
  entryComponents: [AvantageCollabDeleteDialogComponent],
})
export class SigrhpAvantageCollabModule {}
