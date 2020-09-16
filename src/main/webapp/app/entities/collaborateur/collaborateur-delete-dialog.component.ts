import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from './collaborateur.service';

@Component({
  templateUrl: './collaborateur-delete-dialog.component.html',
})
export class CollaborateurDeleteDialogComponent {
  collaborateur?: ICollaborateur;

  constructor(
    protected collaborateurService: CollaborateurService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collaborateurService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collaborateurListModification');
      this.activeModal.close();
    });
  }
}
