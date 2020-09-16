import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INationalite, Nationalite } from 'app/shared/model/nationalite.model';
import { NationaliteService } from './nationalite.service';

@Component({
  selector: 'jhi-nationalite-update',
  templateUrl: './nationalite-update.component.html',
})
export class NationaliteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
  });

  constructor(protected nationaliteService: NationaliteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nationalite }) => {
      this.updateForm(nationalite);
    });
  }

  updateForm(nationalite: INationalite): void {
    this.editForm.patchValue({
      id: nationalite.id,
      libelle: nationalite.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nationalite = this.createFromForm();
    if (nationalite.id !== undefined) {
      this.subscribeToSaveResponse(this.nationaliteService.update(nationalite));
    } else {
      this.subscribeToSaveResponse(this.nationaliteService.create(nationalite));
    }
  }

  private createFromForm(): INationalite {
    return {
      ...new Nationalite(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INationalite>>): void {
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
