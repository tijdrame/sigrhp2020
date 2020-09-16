import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegime } from 'app/shared/model/regime.model';
import { RegimeService } from './regime.service';

@Component({
  templateUrl: './regime-delete-dialog.component.html',
})
export class RegimeDeleteDialogComponent {
  regime?: IRegime;

  constructor(protected regimeService: RegimeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.regimeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('regimeListModification');
      this.activeModal.close();
    });
  }
}
