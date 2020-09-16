import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeRelation } from 'app/shared/model/type-relation.model';
import { TypeRelationService } from './type-relation.service';

@Component({
  templateUrl: './type-relation-delete-dialog.component.html',
})
export class TypeRelationDeleteDialogComponent {
  typeRelation?: ITypeRelation;

  constructor(
    protected typeRelationService: TypeRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeRelationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeRelationListModification');
      this.activeModal.close();
    });
  }
}
