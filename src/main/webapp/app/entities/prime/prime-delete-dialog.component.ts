import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrime } from 'app/shared/model/prime.model';
import { PrimeService } from './prime.service';

@Component({
  templateUrl: './prime-delete-dialog.component.html',
})
export class PrimeDeleteDialogComponent {
  prime?: IPrime;

  constructor(protected primeService: PrimeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.primeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('primeListModification');
      this.activeModal.close();
    });
  }
}
