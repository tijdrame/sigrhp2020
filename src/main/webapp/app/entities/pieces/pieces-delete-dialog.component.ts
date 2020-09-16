import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPieces } from 'app/shared/model/pieces.model';
import { PiecesService } from './pieces.service';

@Component({
  templateUrl: './pieces-delete-dialog.component.html',
})
export class PiecesDeleteDialogComponent {
  pieces?: IPieces;

  constructor(protected piecesService: PiecesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.piecesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('piecesListModification');
      this.activeModal.close();
    });
  }
}
