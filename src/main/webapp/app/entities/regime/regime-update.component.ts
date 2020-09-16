import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegime, Regime } from 'app/shared/model/regime.model';
import { RegimeService } from './regime.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-regime-update',
  templateUrl: './regime-update.component.html',
})
export class RegimeUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    tauxPatronal: [null, [Validators.required]],
    tauxSalarial: [],
    plafond: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected regimeService: RegimeService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regime }) => {
      this.updateForm(regime);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(regime: IRegime): void {
    this.editForm.patchValue({
      id: regime.id,
      libelle: regime.libelle,
      code: regime.code,
      tauxPatronal: regime.tauxPatronal,
      tauxSalarial: regime.tauxSalarial,
      plafond: regime.plafond,
      structure: regime.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const regime = this.createFromForm();
    if (regime.id !== undefined) {
      this.subscribeToSaveResponse(this.regimeService.update(regime));
    } else {
      this.subscribeToSaveResponse(this.regimeService.create(regime));
    }
  }

  private createFromForm(): IRegime {
    return {
      ...new Regime(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      tauxPatronal: this.editForm.get(['tauxPatronal'])!.value,
      tauxSalarial: this.editForm.get(['tauxSalarial'])!.value,
      plafond: this.editForm.get(['plafond'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegime>>): void {
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
