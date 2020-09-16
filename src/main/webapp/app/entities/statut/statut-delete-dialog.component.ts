import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatut } from 'app/shared/model/statut.model';
import { StatutService } from './statut.service';

@Component({
  templateUrl: './statut-delete-dialog.component.html',
})
export class StatutDeleteDialogComponent {
  statut?: IStatut;

  constructor(protected statutService: StatutService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statutService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statutListModification');
      this.activeModal.close();
    });
  }
}
