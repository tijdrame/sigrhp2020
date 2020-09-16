import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPieces, Pieces } from 'app/shared/model/pieces.model';
import { PiecesService } from './pieces.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';

@Component({
  selector: 'jhi-pieces-update',
  templateUrl: './pieces-update.component.html',
})
export class PiecesUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  dateCreationDp: any;
  dateExpirationDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    dateCreation: [null, [Validators.required]],
    dateExpiration: [],
    image: [],
    imageContentType: [],
    collaborateur: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected piecesService: PiecesService,
    protected collaborateurService: CollaborateurService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pieces }) => {
      this.updateForm(pieces);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));
    });
  }

  updateForm(pieces: IPieces): void {
    this.editForm.patchValue({
      id: pieces.id,
      libelle: pieces.libelle,
      dateCreation: pieces.dateCreation,
      dateExpiration: pieces.dateExpiration,
      image: pieces.image,
      imageContentType: pieces.imageContentType,
      collaborateur: pieces.collaborateur,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('sigrhpApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pieces = this.createFromForm();
    if (pieces.id !== undefined) {
      this.subscribeToSaveResponse(this.piecesService.update(pieces));
    } else {
      this.subscribeToSaveResponse(this.piecesService.create(pieces));
    }
  }

  private createFromForm(): IPieces {
    return {
      ...new Pieces(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateExpiration: this.editForm.get(['dateExpiration'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPieces>>): void {
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

  trackById(index: number, item: ICollaborateur): any {
    return item.id;
  }
}
