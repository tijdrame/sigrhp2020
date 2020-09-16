import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { BulletinComponent } from './bulletin.component';
import { BulletinDetailComponent } from './bulletin-detail.component';
import { BulletinUpdateComponent } from './bulletin-update.component';
import { BulletinDeleteDialogComponent } from './bulletin-delete-dialog.component';
import { bulletinRoute } from './bulletin.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(bulletinRoute)],
  declarations: [BulletinComponent, BulletinDetailComponent, BulletinUpdateComponent, BulletinDeleteDialogComponent],
  entryComponents: [BulletinDeleteDialogComponent],
})
export class SigrhpBulletinModule {}
