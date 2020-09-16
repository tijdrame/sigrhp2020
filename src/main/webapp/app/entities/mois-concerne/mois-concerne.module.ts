import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { MoisConcerneComponent } from './mois-concerne.component';
import { MoisConcerneDetailComponent } from './mois-concerne-detail.component';
import { MoisConcerneUpdateComponent } from './mois-concerne-update.component';
import { MoisConcerneDeleteDialogComponent } from './mois-concerne-delete-dialog.component';
import { moisConcerneRoute } from './mois-concerne.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(moisConcerneRoute)],
  declarations: [MoisConcerneComponent, MoisConcerneDetailComponent, MoisConcerneUpdateComponent, MoisConcerneDeleteDialogComponent],
  entryComponents: [MoisConcerneDeleteDialogComponent],
})
export class SigrhpMoisConcerneModule {}
