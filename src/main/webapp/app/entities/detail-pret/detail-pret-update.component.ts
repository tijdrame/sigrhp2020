import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDetailPret, DetailPret } from 'app/shared/model/detail-pret.model';
import { DetailPretService } from './detail-pret.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { IPret } from 'app/shared/model/pret.model';
import { PretService } from 'app/entities/pret/pret.service';

type SelectableEntity = ICollaborateur | IPret;

@Component({
  selector: 'jhi-detail-pret-update',
  templateUrl: './detail-pret-update.component.html',
})
export class DetailPretUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  prets: IPret[] = [];

  editForm = this.fb.group({
    id: [],
    montant: [null, [Validators.required]],
    isRembourse: [],
    collaborateur: [null, Validators.required],
    pret: [null, Validators.required],
  });

  constructor(
    protected detailPretService: DetailPretService,
    protected collaborateurService: CollaborateurService,
    protected pretService: PretService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailPret }) => {
      this.updateForm(detailPret);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.pretService.query().subscribe((res: HttpResponse<IPret[]>) => (this.prets = res.body || []));
    });
  }

  updateForm(detailPret: IDetailPret): void {
    this.editForm.patchValue({
      id: detailPret.id,
      montant: detailPret.montant,
      isRembourse: detailPret.isRembourse,
      collaborateur: detailPret.collaborateur,
      pret: detailPret.pret,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailPret = this.createFromForm();
    if (detailPret.id !== undefined) {
      this.subscribeToSaveResponse(this.detailPretService.update(detailPret));
    } else {
      this.subscribeToSaveResponse(this.detailPretService.create(detailPret));
    }
  }

  private createFromForm(): IDetailPret {
    return {
      ...new DetailPret(),
      id: this.editForm.get(['id'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      isRembourse: this.editForm.get(['isRembourse'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      pret: this.editForm.get(['pret'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailPret>>): void {
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
