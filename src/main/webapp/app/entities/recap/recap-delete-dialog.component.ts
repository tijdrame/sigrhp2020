import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecap } from 'app/shared/model/recap.model';
import { RecapService } from './recap.service';

@Component({
  templateUrl: './recap-delete-dialog.component.html',
})
export class RecapDeleteDialogComponent {
  recap?: IRecap;

  constructor(protected recapService: RecapService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recapService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recapListModification');
      this.activeModal.close();
    });
  }
}
