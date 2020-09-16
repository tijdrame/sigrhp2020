import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeAbsence } from 'app/shared/model/type-absence.model';
import { TypeAbsenceService } from './type-absence.service';

@Component({
  templateUrl: './type-absence-delete-dialog.component.html',
})
export class TypeAbsenceDeleteDialogComponent {
  typeAbsence?: ITypeAbsence;

  constructor(
    protected typeAbsenceService: TypeAbsenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeAbsenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeAbsenceListModification');
      this.activeModal.close();
    });
  }
}
