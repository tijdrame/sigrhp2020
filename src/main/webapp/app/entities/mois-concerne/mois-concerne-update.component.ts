import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMoisConcerne, MoisConcerne } from 'app/shared/model/mois-concerne.model';
import { MoisConcerneService } from './mois-concerne.service';

@Component({
  selector: 'jhi-mois-concerne-update',
  templateUrl: './mois-concerne-update.component.html',
})
export class MoisConcerneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
  });

  constructor(protected moisConcerneService: MoisConcerneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moisConcerne }) => {
      this.updateForm(moisConcerne);
    });
  }

  updateForm(moisConcerne: IMoisConcerne): void {
    this.editForm.patchValue({
      id: moisConcerne.id,
      libelle: moisConcerne.libelle,
      code: moisConcerne.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moisConcerne = this.createFromForm();
    if (moisConcerne.id !== undefined) {
      this.subscribeToSaveResponse(this.moisConcerneService.update(moisConcerne));
    } else {
      this.subscribeToSaveResponse(this.moisConcerneService.create(moisConcerne));
    }
  }

  private createFromForm(): IMoisConcerne {
    return {
      ...new MoisConcerne(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoisConcerne>>): void {
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
