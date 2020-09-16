import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeRelation, TypeRelation } from 'app/shared/model/type-relation.model';
import { TypeRelationService } from './type-relation.service';

@Component({
  selector: 'jhi-type-relation-update',
  templateUrl: './type-relation-update.component.html',
})
export class TypeRelationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    nbParts: [null, [Validators.required]],
  });

  constructor(protected typeRelationService: TypeRelationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeRelation }) => {
      this.updateForm(typeRelation);
    });
  }

  updateForm(typeRelation: ITypeRelation): void {
    this.editForm.patchValue({
      id: typeRelation.id,
      libelle: typeRelation.libelle,
      code: typeRelation.code,
      nbParts: typeRelation.nbParts,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeRelation = this.createFromForm();
    if (typeRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.typeRelationService.update(typeRelation));
    } else {
      this.subscribeToSaveResponse(this.typeRelationService.create(typeRelation));
    }
  }

  private createFromForm(): ITypeRelation {
    return {
      ...new TypeRelation(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      nbParts: this.editForm.get(['nbParts'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeRelation>>): void {
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
