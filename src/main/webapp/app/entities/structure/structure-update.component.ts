import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IStructure, Structure } from 'app/shared/model/structure.model';
import { StructureService } from './structure.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IConvention } from 'app/shared/model/convention.model';
import { ConventionService } from 'app/entities/convention/convention.service';

@Component({
  selector: 'jhi-structure-update',
  templateUrl: './structure-update.component.html',
})
export class StructureUpdateComponent implements OnInit {
  isSaving = false;
  conventions: IConvention[] = [];

  editForm = this.fb.group({
    id: [],
    denomination: [null, [Validators.required]],
    telephone: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    ninea: [null, [Validators.required]],
    capital: [],
    numeroIpres: [null, [Validators.required]],
    numeroCss: [null, [Validators.required]],
    logo: [],
    logoContentType: [],
    ipm: [],
    signature: [],
    signatureContentType: [],
    montantIpm: [],
    convention: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected structureService: StructureService,
    protected conventionService: ConventionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ structure }) => {
      this.updateForm(structure);

      this.conventionService.query().subscribe((res: HttpResponse<IConvention[]>) => (this.conventions = res.body || []));
    });
  }

  updateForm(structure: IStructure): void {
    this.editForm.patchValue({
      id: structure.id,
      denomination: structure.denomination,
      telephone: structure.telephone,
      adresse: structure.adresse,
      ninea: structure.ninea,
      capital: structure.capital,
      numeroIpres: structure.numeroIpres,
      numeroCss: structure.numeroCss,
      logo: structure.logo,
      logoContentType: structure.logoContentType,
      ipm: structure.ipm,
      signature: structure.signature,
      signatureContentType: structure.signatureContentType,
      montantIpm: structure.montantIpm,
      convention: structure.convention,
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
    const structure = this.createFromForm();
    if (structure.id !== undefined) {
      this.subscribeToSaveResponse(this.structureService.update(structure));
    } else {
      this.subscribeToSaveResponse(this.structureService.create(structure));
    }
  }

  private createFromForm(): IStructure {
    return {
      ...new Structure(),
      id: this.editForm.get(['id'])!.value,
      denomination: this.editForm.get(['denomination'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      ninea: this.editForm.get(['ninea'])!.value,
      capital: this.editForm.get(['capital'])!.value,
      numeroIpres: this.editForm.get(['numeroIpres'])!.value,
      numeroCss: this.editForm.get(['numeroCss'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      ipm: this.editForm.get(['ipm'])!.value,
      signatureContentType: this.editForm.get(['signatureContentType'])!.value,
      signature: this.editForm.get(['signature'])!.value,
      montantIpm: this.editForm.get(['montantIpm'])!.value,
      convention: this.editForm.get(['convention'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStructure>>): void {
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

  trackById(index: number, item: IConvention): any {
    return item.id;
  }
}
