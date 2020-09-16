import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBulletin } from 'app/shared/model/bulletin.model';
import { BulletinService } from './bulletin.service';

@Component({
  templateUrl: './bulletin-delete-dialog.component.html',
})
export class BulletinDeleteDialogComponent {
  bulletin?: IBulletin;

  constructor(protected bulletinService: BulletinService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bulletinService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bulletinListModification');
      this.activeModal.close();
    });
  }
}
