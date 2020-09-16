import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDemandeConge } from 'app/shared/model/demande-conge.model';
import { DemandeCongeService } from './demande-conge.service';

@Component({
  templateUrl: './demande-conge-delete-dialog.component.html',
})
export class DemandeCongeDeleteDialogComponent {
  demandeConge?: IDemandeConge;

  constructor(
    protected demandeCongeService: DemandeCongeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeCongeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('demandeCongeListModification');
      this.activeModal.close();
    });
  }
}
