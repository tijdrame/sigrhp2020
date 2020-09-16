import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPret, Pret } from 'app/shared/model/pret.model';
import { PretService } from './pret.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-pret-update',
  templateUrl: './pret-update.component.html',
})
export class PretUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];
  datePretDp: any;
  dateDebutRemboursementDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    nbRemboursement: [null, [Validators.required]],
    datePret: [null, [Validators.required]],
    dateDebutRemboursement: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected pretService: PretService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pret }) => {
      this.updateForm(pret);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(pret: IPret): void {
    this.editForm.patchValue({
      id: pret.id,
      libelle: pret.libelle,
      nbRemboursement: pret.nbRemboursement,
      datePret: pret.datePret,
      dateDebutRemboursement: pret.dateDebutRemboursement,
      structure: pret.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pret = this.createFromForm();
    if (pret.id !== undefined) {
      this.subscribeToSaveResponse(this.pretService.update(pret));
    } else {
      this.subscribeToSaveResponse(this.pretService.create(pret));
    }
  }

  private createFromForm(): IPret {
    return {
      ...new Pret(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      nbRemboursement: this.editForm.get(['nbRemboursement'])!.value,
      datePret: this.editForm.get(['datePret'])!.value,
      dateDebutRemboursement: this.editForm.get(['dateDebutRemboursement'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPret>>): void {
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

  trackById(index: number, item: IStructure): any {
    return item.id;
  }
}
