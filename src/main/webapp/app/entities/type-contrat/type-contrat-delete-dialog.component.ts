import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { TypeContratService } from './type-contrat.service';

@Component({
  templateUrl: './type-contrat-delete-dialog.component.html',
})
export class TypeContratDeleteDialogComponent {
  typeContrat?: ITypeContrat;

  constructor(
    protected typeContratService: TypeContratService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeContratService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeContratListModification');
      this.activeModal.close();
    });
  }
}
