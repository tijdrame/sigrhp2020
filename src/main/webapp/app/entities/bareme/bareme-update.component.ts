import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBareme, Bareme } from 'app/shared/model/bareme.model';
import { BaremeService } from './bareme.service';

@Component({
  selector: 'jhi-bareme-update',
  templateUrl: './bareme-update.component.html',
})
export class BaremeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    revenuBrut: [],
    trimF: [],
    unePart: [],
    unePartEtDemi: [],
    deuxParts: [],
    deuxPartsEtDemi: [],
    troisParts: [],
    troisPartsEtDemi: [],
    quatreParts: [],
    quatrePartsEtDemi: [],
    cinqParts: [],
  });

  constructor(protected baremeService: BaremeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bareme }) => {
      this.updateForm(bareme);
    });
  }

  updateForm(bareme: IBareme): void {
    this.editForm.patchValue({
      id: bareme.id,
      revenuBrut: bareme.revenuBrut,
      trimF: bareme.trimF,
      unePart: bareme.unePart,
      unePartEtDemi: bareme.unePartEtDemi,
      deuxParts: bareme.deuxParts,
      deuxPartsEtDemi: bareme.deuxPartsEtDemi,
      troisParts: bareme.troisParts,
      troisPartsEtDemi: bareme.troisPartsEtDemi,
      quatreParts: bareme.quatreParts,
      quatrePartsEtDemi: bareme.quatrePartsEtDemi,
      cinqParts: bareme.cinqParts,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bareme = this.createFromForm();
    if (bareme.id !== undefined) {
      this.subscribeToSaveResponse(this.baremeService.update(bareme));
    } else {
      this.subscribeToSaveResponse(this.baremeService.create(bareme));
    }
  }

  private createFromForm(): IBareme {
    return {
      ...new Bareme(),
      id: this.editForm.get(['id'])!.value,
      revenuBrut: this.editForm.get(['revenuBrut'])!.value,
      trimF: this.editForm.get(['trimF'])!.value,
      unePart: this.editForm.get(['unePart'])!.value,
      unePartEtDemi: this.editForm.get(['unePartEtDemi'])!.value,
      deuxParts: this.editForm.get(['deuxParts'])!.value,
      deuxPartsEtDemi: this.editForm.get(['deuxPartsEtDemi'])!.value,
      troisParts: this.editForm.get(['troisParts'])!.value,
      troisPartsEtDemi: this.editForm.get(['troisPartsEtDemi'])!.value,
      quatreParts: this.editForm.get(['quatreParts'])!.value,
      quatrePartsEtDemi: this.editForm.get(['quatrePartsEtDemi'])!.value,
      cinqParts: this.editForm.get(['cinqParts'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBareme>>): void {
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
}
