import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetailPret } from 'app/shared/model/detail-pret.model';
import { DetailPretService } from './detail-pret.service';

@Component({
  templateUrl: './detail-pret-delete-dialog.component.html',
})
export class DetailPretDeleteDialogComponent {
  detailPret?: IDetailPret;

  constructor(
    protected detailPretService: DetailPretService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailPretService.delete(id).subscribe(() => {
      this.eventManager.broadcast('detailPretListModification');
      this.activeModal.close();
    });
  }
}
