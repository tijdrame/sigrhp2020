import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMotif } from 'app/shared/model/motif.model';
import { MotifService } from './motif.service';

@Component({
  templateUrl: './motif-delete-dialog.component.html',
})
export class MotifDeleteDialogComponent {
  motif?: IMotif;

  constructor(protected motifService: MotifService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.motifService.delete(id).subscribe(() => {
      this.eventManager.broadcast('motifListModification');
      this.activeModal.close();
    });
  }
}
