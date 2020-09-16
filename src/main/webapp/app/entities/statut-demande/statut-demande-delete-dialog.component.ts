import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatutDemande } from 'app/shared/model/statut-demande.model';
import { StatutDemandeService } from './statut-demande.service';

@Component({
  templateUrl: './statut-demande-delete-dialog.component.html',
})
export class StatutDemandeDeleteDialogComponent {
  statutDemande?: IStatutDemande;

  constructor(
    protected statutDemandeService: StatutDemandeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statutDemandeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statutDemandeListModification');
      this.activeModal.close();
    });
  }
}
