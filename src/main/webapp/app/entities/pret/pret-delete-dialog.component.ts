import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPret } from 'app/shared/model/pret.model';
import { PretService } from './pret.service';

@Component({
  templateUrl: './pret-delete-dialog.component.html',
})
export class PretDeleteDialogComponent {
  pret?: IPret;

  constructor(protected pretService: PretService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pretService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pretListModification');
      this.activeModal.close();
    });
  }
}
