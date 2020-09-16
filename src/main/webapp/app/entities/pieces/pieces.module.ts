import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { PiecesComponent } from './pieces.component';
import { PiecesDetailComponent } from './pieces-detail.component';
import { PiecesUpdateComponent } from './pieces-update.component';
import { PiecesDeleteDialogComponent } from './pieces-delete-dialog.component';
import { piecesRoute } from './pieces.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(piecesRoute)],
  declarations: [PiecesComponent, PiecesDetailComponent, PiecesUpdateComponent, PiecesDeleteDialogComponent],
  entryComponents: [PiecesDeleteDialogComponent],
})
export class SigrhpPiecesModule {}
