import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrimeCollab } from 'app/shared/model/prime-collab.model';
import { PrimeCollabService } from './prime-collab.service';

@Component({
  templateUrl: './prime-collab-delete-dialog.component.html',
})
export class PrimeCollabDeleteDialogComponent {
  primeCollab?: IPrimeCollab;

  constructor(
    protected primeCollabService: PrimeCollabService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.primeCollabService.delete(id).subscribe(() => {
      this.eventManager.broadcast('primeCollabListModification');
      this.activeModal.close();
    });
  }
}
