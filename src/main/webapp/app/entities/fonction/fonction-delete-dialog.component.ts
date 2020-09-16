import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFonction } from 'app/shared/model/fonction.model';
import { FonctionService } from './fonction.service';

@Component({
  templateUrl: './fonction-delete-dialog.component.html',
})
export class FonctionDeleteDialogComponent {
  fonction?: IFonction;

  constructor(protected fonctionService: FonctionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fonctionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fonctionListModification');
      this.activeModal.close();
    });
  }
}
