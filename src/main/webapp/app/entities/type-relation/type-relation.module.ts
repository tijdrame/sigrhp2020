import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigrhpSharedModule } from 'app/shared/shared.module';
import { TypeRelationComponent } from './type-relation.component';
import { TypeRelationDetailComponent } from './type-relation-detail.component';
import { TypeRelationUpdateComponent } from './type-relation-update.component';
import { TypeRelationDeleteDialogComponent } from './type-relation-delete-dialog.component';
import { typeRelationRoute } from './type-relation.route';

@NgModule({
  imports: [SigrhpSharedModule, RouterModule.forChild(typeRelationRoute)],
  declarations: [TypeRelationComponent, TypeRelationDetailComponent, TypeRelationUpdateComponent, TypeRelationDeleteDialogComponent],
  entryComponents: [TypeRelationDeleteDialogComponent],
})
export class SigrhpTypeRelationModule {}
