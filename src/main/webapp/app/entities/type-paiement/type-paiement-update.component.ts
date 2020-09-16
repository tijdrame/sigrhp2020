import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypePaiement, TypePaiement } from 'app/shared/model/type-paiement.model';
import { TypePaiementService } from './type-paiement.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-type-paiement-update',
  templateUrl: './type-paiement-update.component.html',
})
export class TypePaiementUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected typePaiementService: TypePaiementService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePaiement }) => {
      this.updateForm(typePaiement);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(typePaiement: ITypePaiement): void {
    this.editForm.patchValue({
      id: typePaiement.id,
      libelle: typePaiement.libelle,
      code: typePaiement.code,
      structure: typePaiement.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typePaiement = this.createFromForm();
    if (typePaiement.id !== undefined) {
      this.subscribeToSaveResponse(this.typePaiementService.update(typePaiement));
    } else {
      this.subscribeToSaveResponse(this.typePaiementService.create(typePaiement));
    }
  }

  private createFromForm(): ITypePaiement {
    return {
      ...new TypePaiement(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypePaiement>>): void {
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
