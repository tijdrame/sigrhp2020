import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISexe } from 'app/shared/model/sexe.model';
import { SexeService } from './sexe.service';

@Component({
  templateUrl: './sexe-delete-dialog.component.html',
})
export class SexeDeleteDialogComponent {
  sexe?: ISexe;

  constructor(protected sexeService: SexeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sexeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sexeListModification');
      this.activeModal.close();
    });
  }
}
