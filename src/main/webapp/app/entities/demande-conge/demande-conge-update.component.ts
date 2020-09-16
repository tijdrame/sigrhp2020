import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDemandeConge, DemandeConge } from 'app/shared/model/demande-conge.model';
import { DemandeCongeService } from './demande-conge.service';
import { IStatutDemande } from 'app/shared/model/statut-demande.model';
import { StatutDemandeService } from 'app/entities/statut-demande/statut-demande.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { ITypeAbsence } from 'app/shared/model/type-absence.model';
import { TypeAbsenceService } from 'app/entities/type-absence/type-absence.service';

type SelectableEntity = IStatutDemande | ICollaborateur | ITypeAbsence;

@Component({
  selector: 'jhi-demande-conge-update',
  templateUrl: './demande-conge-update.component.html',
})
export class DemandeCongeUpdateComponent implements OnInit {
  isSaving = false;
  statutdemandes: IStatutDemande[] = [];
  collaborateurs: ICollaborateur[] = [];
  typeabsences: ITypeAbsence[] = [];
  dateDebutDp: any;
  dateFinDp: any;

  editForm = this.fb.group({
    id: [],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    motifRejet: [],
    libelle: [null, [Validators.required]],
    statutRH: [],
    statutDG: [],
    collaborateur: [],
    typeAbsence: [null, Validators.required],
  });

  constructor(
    protected demandeCongeService: DemandeCongeService,
    protected statutDemandeService: StatutDemandeService,
    protected collaborateurService: CollaborateurService,
    protected typeAbsenceService: TypeAbsenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeConge }) => {
      this.updateForm(demandeConge);

      this.statutDemandeService.query().subscribe((res: HttpResponse<IStatutDemande[]>) => (this.statutdemandes = res.body || []));

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.typeAbsenceService.query().subscribe((res: HttpResponse<ITypeAbsence[]>) => (this.typeabsences = res.body || []));
    });
  }

  updateForm(demandeConge: IDemandeConge): void {
    this.editForm.patchValue({
      id: demandeConge.id,
      dateDebut: demandeConge.dateDebut,
      dateFin: demandeConge.dateFin,
      motifRejet: demandeConge.motifRejet,
      libelle: demandeConge.libelle,
      statutRH: demandeConge.statutRH,
      statutDG: demandeConge.statutDG,
      collaborateur: demandeConge.collaborateur,
      typeAbsence: demandeConge.typeAbsence,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeConge = this.createFromForm();
    if (demandeConge.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeCongeService.update(demandeConge));
    } else {
      this.subscribeToSaveResponse(this.demandeCongeService.create(demandeConge));
    }
  }

  private createFromForm(): IDemandeConge {
    return {
      ...new DemandeConge(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      motifRejet: this.editForm.get(['motifRejet'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      statutRH: this.editForm.get(['statutRH'])!.value,
      statutDG: this.editForm.get(['statutDG'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      typeAbsence: this.editForm.get(['typeAbsence'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeConge>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
