import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMembreFamille, MembreFamille } from 'app/shared/model/membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { ISexe } from 'app/shared/model/sexe.model';
import { SexeService } from 'app/entities/sexe/sexe.service';
import { ITypeRelation } from 'app/shared/model/type-relation.model';
import { TypeRelationService } from 'app/entities/type-relation/type-relation.service';

type SelectableEntity = ICollaborateur | ISexe | ITypeRelation;

@Component({
  selector: 'jhi-membre-famille-update',
  templateUrl: './membre-famille-update.component.html',
})
export class MembreFamilleUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  sexes: ISexe[] = [];
  typerelations: ITypeRelation[] = [];
  dateNaissanceDp: any;
  dateMariageDp: any;

  editForm = this.fb.group({
    id: [],
    prenom: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    telephone: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    dateMariage: [],
    photo: [],
    photoContentType: [],
    isActif: [],
    collaborateur: [null, Validators.required],
    sexe: [null, Validators.required],
    typeRelation: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected membreFamilleService: MembreFamilleService,
    protected collaborateurService: CollaborateurService,
    protected sexeService: SexeService,
    protected typeRelationService: TypeRelationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreFamille }) => {
      this.updateForm(membreFamille);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.sexeService.query().subscribe((res: HttpResponse<ISexe[]>) => (this.sexes = res.body || []));

      this.typeRelationService.query().subscribe((res: HttpResponse<ITypeRelation[]>) => (this.typerelations = res.body || []));
    });
  }

  updateForm(membreFamille: IMembreFamille): void {
    this.editForm.patchValue({
      id: membreFamille.id,
      prenom: membreFamille.prenom,
      nom: membreFamille.nom,
      adresse: membreFamille.adresse,
      telephone: membreFamille.telephone,
      dateNaissance: membreFamille.dateNaissance,
      dateMariage: membreFamille.dateMariage,
      photo: membreFamille.photo,
      photoContentType: membreFamille.photoContentType,
      isActif: membreFamille.isActif,
      collaborateur: membreFamille.collaborateur,
      sexe: membreFamille.sexe,
      typeRelation: membreFamille.typeRelation,
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
    const membreFamille = this.createFromForm();
    if (membreFamille.id !== undefined) {
      this.subscribeToSaveResponse(this.membreFamilleService.update(membreFamille));
    } else {
      this.subscribeToSaveResponse(this.membreFamilleService.create(membreFamille));
    }
  }

  private createFromForm(): IMembreFamille {
    return {
      ...new MembreFamille(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      dateMariage: this.editForm.get(['dateMariage'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      typeRelation: this.editForm.get(['typeRelation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreFamille>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
