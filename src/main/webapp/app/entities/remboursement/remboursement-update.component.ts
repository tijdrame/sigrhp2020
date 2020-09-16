import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRemboursement, Remboursement } from 'app/shared/model/remboursement.model';
import { RemboursementService } from './remboursement.service';
import { IDetailPret } from 'app/shared/model/detail-pret.model';
import { DetailPretService } from 'app/entities/detail-pret/detail-pret.service';

@Component({
  selector: 'jhi-remboursement-update',
  templateUrl: './remboursement-update.component.html',
})
export class RemboursementUpdateComponent implements OnInit {
  isSaving = false;
  detailprets: IDetailPret[] = [];
  dateRemboursementDp: any;

  editForm = this.fb.group({
    id: [],
    dateRemboursement: [null, [Validators.required]],
    montant: [null, [Validators.required]],
    isRembourse: [],
    detailPret: [null, Validators.required],
  });

  constructor(
    protected remboursementService: RemboursementService,
    protected detailPretService: DetailPretService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remboursement }) => {
      this.updateForm(remboursement);

      this.detailPretService.query().subscribe((res: HttpResponse<IDetailPret[]>) => (this.detailprets = res.body || []));
    });
  }

  updateForm(remboursement: IRemboursement): void {
    this.editForm.patchValue({
      id: remboursement.id,
      dateRemboursement: remboursement.dateRemboursement,
      montant: remboursement.montant,
      isRembourse: remboursement.isRembourse,
      detailPret: remboursement.detailPret,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const remboursement = this.createFromForm();
    if (remboursement.id !== undefined) {
      this.subscribeToSaveResponse(this.remboursementService.update(remboursement));
    } else {
      this.subscribeToSaveResponse(this.remboursementService.create(remboursement));
    }
  }

  private createFromForm(): IRemboursement {
    return {
      ...new Remboursement(),
      id: this.editForm.get(['id'])!.value,
      dateRemboursement: this.editForm.get(['dateRemboursement'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      isRembourse: this.editForm.get(['isRembourse'])!.value,
      detailPret: this.editForm.get(['detailPret'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemboursement>>): void {
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

  trackById(index: number, item: IDetailPret): any {
    return item.id;
  }
}
