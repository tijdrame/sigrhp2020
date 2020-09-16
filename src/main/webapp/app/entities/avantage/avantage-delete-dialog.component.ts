import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvantage } from 'app/shared/model/avantage.model';
import { AvantageService } from './avantage.service';

@Component({
  templateUrl: './avantage-delete-dialog.component.html',
})
export class AvantageDeleteDialogComponent {
  avantage?: IAvantage;

  constructor(protected avantageService: AvantageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avantageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avantageListModification');
      this.activeModal.close();
    });
  }
}
