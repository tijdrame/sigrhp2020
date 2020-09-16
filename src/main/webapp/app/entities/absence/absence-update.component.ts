import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAbsence, Absence } from 'app/shared/model/absence.model';
import { AbsenceService } from './absence.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { IMotif } from 'app/shared/model/motif.model';
import { MotifService } from 'app/entities/motif/motif.service';
import { IExercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from 'app/entities/exercice/exercice.service';

type SelectableEntity = ICollaborateur | IMotif | IExercice;

@Component({
  selector: 'jhi-absence-update',
  templateUrl: './absence-update.component.html',
})
export class AbsenceUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  motifs: IMotif[] = [];
  exercices: IExercice[] = [];
  dateAbsenceDp: any;

  editForm = this.fb.group({
    id: [],
    dateAbsence: [null, [Validators.required]],
    collaborateur: [null, Validators.required],
    motif: [null, Validators.required],
    exercice: [null, Validators.required],
  });

  constructor(
    protected absenceService: AbsenceService,
    protected collaborateurService: CollaborateurService,
    protected motifService: MotifService,
    protected exerciceService: ExerciceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ absence }) => {
      this.updateForm(absence);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.motifService.query().subscribe((res: HttpResponse<IMotif[]>) => (this.motifs = res.body || []));

      this.exerciceService.query().subscribe((res: HttpResponse<IExercice[]>) => (this.exercices = res.body || []));
    });
  }

  updateForm(absence: IAbsence): void {
    this.editForm.patchValue({
      id: absence.id,
      dateAbsence: absence.dateAbsence,
      collaborateur: absence.collaborateur,
      motif: absence.motif,
      exercice: absence.exercice,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const absence = this.createFromForm();
    if (absence.id !== undefined) {
      this.subscribeToSaveResponse(this.absenceService.update(absence));
    } else {
      this.subscribeToSaveResponse(this.absenceService.create(absence));
    }
  }

  private createFromForm(): IAbsence {
    return {
      ...new Absence(),
      id: this.editForm.get(['id'])!.value,
      dateAbsence: this.editForm.get(['dateAbsence'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      motif: this.editForm.get(['motif'])!.value,
      exercice: this.editForm.get(['exercice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbsence>>): void {
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
