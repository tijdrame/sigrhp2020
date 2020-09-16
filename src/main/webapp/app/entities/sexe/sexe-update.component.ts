import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISexe, Sexe } from 'app/shared/model/sexe.model';
import { SexeService } from './sexe.service';

@Component({
  selector: 'jhi-sexe-update',
  templateUrl: './sexe-update.component.html',
})
export class SexeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
  });

  constructor(protected sexeService: SexeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sexe }) => {
      this.updateForm(sexe);
    });
  }

  updateForm(sexe: ISexe): void {
    this.editForm.patchValue({
      id: sexe.id,
      libelle: sexe.libelle,
      code: sexe.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sexe = this.createFromForm();
    if (sexe.id !== undefined) {
      this.subscribeToSaveResponse(this.sexeService.update(sexe));
    } else {
      this.subscribeToSaveResponse(this.sexeService.create(sexe));
    }
  }

  private createFromForm(): ISexe {
    return {
      ...new Sexe(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISexe>>): void {
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
