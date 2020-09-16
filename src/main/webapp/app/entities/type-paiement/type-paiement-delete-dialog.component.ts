import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePaiement } from 'app/shared/model/type-paiement.model';
import { TypePaiementService } from './type-paiement.service';

@Component({
  templateUrl: './type-paiement-delete-dialog.component.html',
})
export class TypePaiementDeleteDialogComponent {
  typePaiement?: ITypePaiement;

  constructor(
    protected typePaiementService: TypePaiementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typePaiementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typePaiementListModification');
      this.activeModal.close();
    });
  }
}
