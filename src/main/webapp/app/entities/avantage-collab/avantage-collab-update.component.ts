import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvantageCollab, AvantageCollab } from 'app/shared/model/avantage-collab.model';
import { AvantageCollabService } from './avantage-collab.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { IAvantage } from 'app/shared/model/avantage.model';
import { AvantageService } from 'app/entities/avantage/avantage.service';

type SelectableEntity = ICollaborateur | IAvantage;

@Component({
  selector: 'jhi-avantage-collab-update',
  templateUrl: './avantage-collab-update.component.html',
})
export class AvantageCollabUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  avantages: IAvantage[] = [];

  editForm = this.fb.group({
    id: [],
    montant: [null, [Validators.required]],
    collaborateur: [null, Validators.required],
    avantage: [null, Validators.required],
  });

  constructor(
    protected avantageCollabService: AvantageCollabService,
    protected collaborateurService: CollaborateurService,
    protected avantageService: AvantageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avantageCollab }) => {
      this.updateForm(avantageCollab);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.avantageService.query().subscribe((res: HttpResponse<IAvantage[]>) => (this.avantages = res.body || []));
    });
  }

  updateForm(avantageCollab: IAvantageCollab): void {
    this.editForm.patchValue({
      id: avantageCollab.id,
      montant: avantageCollab.montant,
      collaborateur: avantageCollab.collaborateur,
      avantage: avantageCollab.avantage,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avantageCollab = this.createFromForm();
    if (avantageCollab.id !== undefined) {
      this.subscribeToSaveResponse(this.avantageCollabService.update(avantageCollab));
    } else {
      this.subscribeToSaveResponse(this.avantageCollabService.create(avantageCollab));
    }
  }

  private createFromForm(): IAvantageCollab {
    return {
      ...new AvantageCollab(),
      id: this.editForm.get(['id'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      avantage: this.editForm.get(['avantage'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvantageCollab>>): void {
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
