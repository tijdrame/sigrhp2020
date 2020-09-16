import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';

@Component({
  templateUrl: './situation-matrimoniale-delete-dialog.component.html',
})
export class SituationMatrimonialeDeleteDialogComponent {
  situationMatrimoniale?: ISituationMatrimoniale;

  constructor(
    protected situationMatrimonialeService: SituationMatrimonialeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.situationMatrimonialeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('situationMatrimonialeListModification');
      this.activeModal.close();
    });
  }
}
