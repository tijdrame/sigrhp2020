import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { DemandeCongeComponent } from './demande-conge.component';
import { DemandeCongeDetailComponent } from './demande-conge-detail.component';
import { DemandeCongeUpdateComponent } from './demande-conge-update.component';
import { DemandeCongeDeleteDialogComponent } from './demande-conge-delete-dialog.component';
import { demandeCongeRoute } from './demande-conge.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(demandeCongeRoute)],
  declarations: [DemandeCongeComponent, DemandeCongeDetailComponent, DemandeCongeUpdateComponent, DemandeCongeDeleteDialogComponent],
  entryComponents: [DemandeCongeDeleteDialogComponent],
})
export class SigrhpDemandeCongeModule {}
