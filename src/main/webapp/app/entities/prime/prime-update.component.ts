import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPrime, Prime } from 'app/shared/model/prime.model';
import { PrimeService } from './prime.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

@Component({
  selector: 'jhi-prime-update',
  templateUrl: './prime-update.component.html',
})
export class PrimeUpdateComponent implements OnInit {
  isSaving = false;
  structures: IStructure[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    imposable: [],
    structure: [],
  });

  constructor(
    protected primeService: PrimeService,
    protected structureService: StructureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prime }) => {
      this.updateForm(prime);

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(prime: IPrime): void {
    this.editForm.patchValue({
      id: prime.id,
      libelle: prime.libelle,
      code: prime.code,
      imposable: prime.imposable,
      structure: prime.structure,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prime = this.createFromForm();
    if (prime.id !== undefined) {
      this.subscribeToSaveResponse(this.primeService.update(prime));
    } else {
      this.subscribeToSaveResponse(this.primeService.create(prime));
    }
  }

  private createFromForm(): IPrime {
    return {
      ...new Prime(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      imposable: this.editForm.get(['imposable'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrime>>): void {
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
