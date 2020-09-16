import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeAbsence, TypeAbsence } from 'app/shared/model/type-absence.model';
import { TypeAbsenceService } from './type-absence.service';

@Component({
  selector: 'jhi-type-absence-update',
  templateUrl: './type-absence-update.component.html',
})
export class TypeAbsenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
  });

  constructor(protected typeAbsenceService: TypeAbsenceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAbsence }) => {
      this.updateForm(typeAbsence);
    });
  }

  updateForm(typeAbsence: ITypeAbsence): void {
    this.editForm.patchValue({
      id: typeAbsence.id,
      libelle: typeAbsence.libelle,
      code: typeAbsence.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAbsence = this.createFromForm();
    if (typeAbsence.id !== undefined) {
      this.subscribeToSaveResponse(this.typeAbsenceService.update(typeAbsence));
    } else {
      this.subscribeToSaveResponse(this.typeAbsenceService.create(typeAbsence));
    }
  }

  private createFromForm(): ITypeAbsence {
    return {
      ...new TypeAbsence(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAbsence>>): void {
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
}
