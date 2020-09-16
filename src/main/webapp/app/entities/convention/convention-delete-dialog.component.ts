import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConvention } from 'app/shared/model/convention.model';
import { ConventionService } from './convention.service';

@Component({
  templateUrl: './convention-delete-dialog.component.html',
})
export class ConventionDeleteDialogComponent {
  convention?: IConvention;

  constructor(
    protected conventionService: ConventionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conventionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conventionListModification');
      this.activeModal.close();
    });
  }
}
