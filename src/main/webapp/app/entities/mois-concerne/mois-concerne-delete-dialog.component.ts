import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoisConcerne } from 'app/shared/model/mois-concerne.model';
import { MoisConcerneService } from './mois-concerne.service';

@Component({
  templateUrl: './mois-concerne-delete-dialog.component.html',
})
export class MoisConcerneDeleteDialogComponent {
  moisConcerne?: IMoisConcerne;

  constructor(
    protected moisConcerneService: MoisConcerneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moisConcerneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('moisConcerneListModification');
      this.activeModal.close();
    });
  }
}
