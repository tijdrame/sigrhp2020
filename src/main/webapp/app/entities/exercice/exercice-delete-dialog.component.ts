import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from './exercice.service';

@Component({
  templateUrl: './exercice-delete-dialog.component.html',
})
export class ExerciceDeleteDialogComponent {
  exercice?: IExercice;

  constructor(protected exerciceService: ExerciceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.exerciceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('exerciceListModification');
      this.activeModal.close();
    });
  }
}
