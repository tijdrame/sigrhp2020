import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvantageCollab } from 'app/shared/model/avantage-collab.model';
import { AvantageCollabService } from './avantage-collab.service';

@Component({
  templateUrl: './avantage-collab-delete-dialog.component.html',
})
export class AvantageCollabDeleteDialogComponent {
  avantageCollab?: IAvantageCollab;

  constructor(
    protected avantageCollabService: AvantageCollabService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avantageCollabService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avantageCollabListModification');
      this.activeModal.close();
    });
  }
}
