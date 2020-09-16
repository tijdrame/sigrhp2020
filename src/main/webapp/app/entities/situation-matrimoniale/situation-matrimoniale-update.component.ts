import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISituationMatrimoniale, SituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-situation-matrimoniale-update',
  templateUrl: './situation-matrimoniale-update.component.html',
})
export class SituationMatrimonialeUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    nbParts: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected situationMatrimonialeService: SituationMatrimonialeService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situationMatrimoniale }) => {
      this.updateForm(situationMatrimoniale);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(situationMatrimoniale: ISituationMatrimoniale): void {
    this.editForm.patchValue({
      id: situationMatrimoniale.id,
      libelle: situationMatrimoniale.libelle,
      code: situationMatrimoniale.code,
      nbParts: situationMatrimoniale.nbParts,
      structure: situationMatrimoniale.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const situationMatrimoniale = this.createFromForm();
    if (situationMatrimoniale.id !== undefined) {
      this.subscribeToSaveResponse(this.situationMatrimonialeService.update(situationMatrimoniale));
    } else {
      this.subscribeToSaveResponse(this.situationMatrimonialeService.create(situationMatrimoniale));
    }
  }

  private createFromForm(): ISituationMatrimoniale {
    return {
      ...new SituationMatrimoniale(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      nbParts: this.editForm.get(['nbParts'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituationMatrimoniale>>): void {
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
