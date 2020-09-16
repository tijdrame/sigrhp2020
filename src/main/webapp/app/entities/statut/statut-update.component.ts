import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatut, Statut } from 'app/shared/model/statut.model';
import { StatutService } from './statut.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-statut-update',
  templateUrl: './statut-update.component.html',
})
export class StatutUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected statutService: StatutService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statut }) => {
      this.updateForm(statut);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(statut: IStatut): void {
    this.editForm.patchValue({
      id: statut.id,
      libelle: statut.libelle,
      code: statut.code,
      structure: statut.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statut = this.createFromForm();
    if (statut.id !== undefined) {
      this.subscribeToSaveResponse(this.statutService.update(statut));
    } else {
      this.subscribeToSaveResponse(this.statutService.create(statut));
    }
  }

  private createFromForm(): IStatut {
    return {
      ...new Statut(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatut>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IStructure): any {
    return item.id;
  }
}
