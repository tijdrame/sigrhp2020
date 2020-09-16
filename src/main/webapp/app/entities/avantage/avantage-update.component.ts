import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvantage, Avantage } from 'app/shared/model/avantage.model';
import { AvantageService } from './avantage.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-avantage-update',
  templateUrl: './avantage-update.component.html',
})
export class AvantageUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    structure: [],
  });

  constructor(
    protected avantageService: AvantageService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avantage }) => {
      this.updateForm(avantage);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(avantage: IAvantage): void {
    this.editForm.patchValue({
      id: avantage.id,
      libelle: avantage.libelle,
      code: avantage.code,
      structure: avantage.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avantage = this.createFromForm();
    if (avantage.id !== undefined) {
      this.subscribeToSaveResponse(this.avantageService.update(avantage));
    } else {
      this.subscribeToSaveResponse(this.avantageService.create(avantage));
    }
  }

  private createFromForm(): IAvantage {
    return {
      ...new Avantage(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvantage>>): void {
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
