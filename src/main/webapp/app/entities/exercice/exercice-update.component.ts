import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExercice, Exercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from './exercice.service';

@Component({
  selector: 'jhi-exercice-update',
  templateUrl: './exercice-update.component.html',
})
export class ExerciceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    debutExercice: [null, [Validators.required]],
    finExercice: [],
    actif: [],
  });

  constructor(protected exerciceService: ExerciceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exercice }) => {
      this.updateForm(exercice);
    });
  }

  updateForm(exercice: IExercice): void {
    this.editForm.patchValue({
      id: exercice.id,
      debutExercice: exercice.debutExercice,
      finExercice: exercice.finExercice,
      actif: exercice.actif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exercice = this.createFromForm();
    if (exercice.id !== undefined) {
      this.subscribeToSaveResponse(this.exerciceService.update(exercice));
    } else {
      this.subscribeToSaveResponse(this.exerciceService.create(exercice));
    }
  }

  private createFromForm(): IExercice {
    return {
      ...new Exercice(),
      id: this.editForm.get(['id'])!.value,
      debutExercice: this.editForm.get(['debutExercice'])!.value,
      finExercice: this.editForm.get(['finExercice'])!.value,
      actif: this.editForm.get(['actif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExercice>>): void {
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
