import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBareme } from 'app/shared/model/bareme.model';
import { BaremeService } from './bareme.service';

@Component({
  templateUrl: './bareme-delete-dialog.component.html',
})
export class BaremeDeleteDialogComponent {
  bareme?: IBareme;

  constructor(protected baremeService: BaremeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.baremeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('baremeListModification');
      this.activeModal.close();
    });
  }
}
