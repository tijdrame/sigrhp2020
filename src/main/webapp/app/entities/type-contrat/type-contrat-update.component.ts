import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeContrat, TypeContrat } from 'app/shared/model/type-contrat.model';
import { TypeContratService } from './type-contrat.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-type-contrat-update',
  templateUrl: './type-contrat-update.component.html',
})
export class TypeContratUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected typeContratService: TypeContratService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeContrat }) => {
      this.updateForm(typeContrat);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(typeContrat: ITypeContrat): void {
    this.editForm.patchValue({
      id: typeContrat.id,
      libelle: typeContrat.libelle,
      code: typeContrat.code,
      structure: typeContrat.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeContrat = this.createFromForm();
    if (typeContrat.id !== undefined) {
      this.subscribeToSaveResponse(this.typeContratService.update(typeContrat));
    } else {
      this.subscribeToSaveResponse(this.typeContratService.create(typeContrat));
    }
  }

  private createFromForm(): ITypeContrat {
    return {
      ...new TypeContrat(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeContrat>>): void {
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
