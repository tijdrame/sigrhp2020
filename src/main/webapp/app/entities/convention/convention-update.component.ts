import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConvention, Convention } from 'app/shared/model/convention.model';
import { ConventionService } from './convention.service';

@Component({
  selector: 'jhi-convention-update',
  templateUrl: './convention-update.component.html',
})
export class ConventionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
  });

  constructor(protected conventionService: ConventionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ convention }) => {
      this.updateForm(convention);
    });
  }

  updateForm(convention: IConvention): void {
    this.editForm.patchValue({
      id: convention.id,
      libelle: convention.libelle,
      code: convention.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const convention = this.createFromForm();
    if (convention.id !== undefined) {
      this.subscribeToSaveResponse(this.conventionService.update(convention));
    } else {
      this.subscribeToSaveResponse(this.conventionService.create(convention));
    }
  }

  private createFromForm(): IConvention {
    return {
      ...new Convention(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConvention>>): void {
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
