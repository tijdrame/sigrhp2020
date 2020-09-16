import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from './structure.service';

@Component({
  templateUrl: './structure-delete-dialog.component.html',
})
export class StructureDeleteDialogComponent {
  structure?: IStructure;

  constructor(protected structureService: StructureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.structureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('structureListModification');
      this.activeModal.close();
    });
  }
}
