import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INationalite } from 'app/shared/model/nationalite.model';
import { NationaliteService } from './nationalite.service';

@Component({
  templateUrl: './nationalite-delete-dialog.component.html',
})
export class NationaliteDeleteDialogComponent {
  nationalite?: INationalite;

  constructor(
    protected nationaliteService: NationaliteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nationaliteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nationaliteListModification');
      this.activeModal.close();
    });
  }
}
