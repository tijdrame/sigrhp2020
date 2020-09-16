import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatutDemande, StatutDemande } from 'app/shared/model/statut-demande.model';
import { StatutDemandeService } from './statut-demande.service';

@Component({
  selector: 'jhi-statut-demande-update',
  templateUrl: './statut-demande-update.component.html',
})
export class StatutDemandeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
  });

  constructor(protected statutDemandeService: StatutDemandeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statutDemande }) => {
      this.updateForm(statutDemande);
    });
  }

  updateForm(statutDemande: IStatutDemande): void {
    this.editForm.patchValue({
      id: statutDemande.id,
      libelle: statutDemande.libelle,
      code: statutDemande.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statutDemande = this.createFromForm();
    if (statutDemande.id !== undefined) {
      this.subscribeToSaveResponse(this.statutDemandeService.update(statutDemande));
    } else {
      this.subscribeToSaveResponse(this.statutDemandeService.create(statutDemande));
    }
  }

  private createFromForm(): IStatutDemande {
    return {
      ...new StatutDemande(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatutDemande>>): void {
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
