import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembreFamille } from 'app/shared/model/membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';

@Component({
  templateUrl: './membre-famille-delete-dialog.component.html',
})
export class MembreFamilleDeleteDialogComponent {
  membreFamille?: IMembreFamille;

  constructor(
    protected membreFamilleService: MembreFamilleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.membreFamilleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('membreFamilleListModification');
      this.activeModal.close();
    });
  }
}
