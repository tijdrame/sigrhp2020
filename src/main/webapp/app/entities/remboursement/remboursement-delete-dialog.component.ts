import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRemboursement } from 'app/shared/model/remboursement.model';
import { RemboursementService } from './remboursement.service';

@Component({
  templateUrl: './remboursement-delete-dialog.component.html',
})
export class RemboursementDeleteDialogComponent {
  remboursement?: IRemboursement;

  constructor(
    protected remboursementService: RemboursementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.remboursementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('remboursementListModification');
      this.activeModal.close();
    });
  }
}
