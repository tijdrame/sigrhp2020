import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecap, Recap } from 'app/shared/model/recap.model';
import { RecapService } from './recap.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';

@Component({
  selector: 'jhi-recap-update',
  templateUrl: './recap-update.component.html',
})
export class RecapUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];

  editForm = this.fb.group({
    id: [],
    brutFiscal: [],
    netAPayer: [],
    salaireBrutMensuel: [],
    impotSurRevenu: [],
    trimf: [],
    ipresPartSalariale: [],
    totRetenue: [],
    ipresPartPatronales: [],
    cssAccidentDeTravail: [],
    cssPrestationFamiliale: [],
    ipmPatronale: [],
    contributionForfaitaire: [],
    primeImposable: [],
    primeNonImposable: [],
    avantage: [],
    recapLigne: [],
    collaborateur: [],
  });

  constructor(
    protected recapService: RecapService,
    protected collaborateurService: CollaborateurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recap }) => {
      this.updateForm(recap);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));
    });
  }

  updateForm(recap: IRecap): void {
    this.editForm.patchValue({
      id: recap.id,
      brutFiscal: recap.brutFiscal,
      netAPayer: recap.netAPayer,
      salaireBrutMensuel: recap.salaireBrutMensuel,
      impotSurRevenu: recap.impotSurRevenu,
      trimf: recap.trimf,
      ipresPartSalariale: recap.ipresPartSalariale,
      totRetenue: recap.totRetenue,
      ipresPartPatronales: recap.ipresPartPatronales,
      cssAccidentDeTravail: recap.cssAccidentDeTravail,
      cssPrestationFamiliale: recap.cssPrestationFamiliale,
      ipmPatronale: recap.ipmPatronale,
      contributionForfaitaire: recap.contributionForfaitaire,
      primeImposable: recap.primeImposable,
      primeNonImposable: recap.primeNonImposable,
      avantage: recap.avantage,
      recapLigne: recap.recapLigne,
      collaborateur: recap.collaborateur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recap = this.createFromForm();
    if (recap.id !== undefined) {
      this.subscribeToSaveResponse(this.recapService.update(recap));
    } else {
      this.subscribeToSaveResponse(this.recapService.create(recap));
    }
  }

  private createFromForm(): IRecap {
    return {
      ...new Recap(),
      id: this.editForm.get(['id'])!.value,
      brutFiscal: this.editForm.get(['brutFiscal'])!.value,
      netAPayer: this.editForm.get(['netAPayer'])!.value,
      salaireBrutMensuel: this.editForm.get(['salaireBrutMensuel'])!.value,
      impotSurRevenu: this.editForm.get(['impotSurRevenu'])!.value,
      trimf: this.editForm.get(['trimf'])!.value,
      ipresPartSalariale: this.editForm.get(['ipresPartSalariale'])!.value,
      totRetenue: this.editForm.get(['totRetenue'])!.value,
      ipresPartPatronales: this.editForm.get(['ipresPartPatronales'])!.value,
      cssAccidentDeTravail: this.editForm.get(['cssAccidentDeTravail'])!.value,
      cssPrestationFamiliale: this.editForm.get(['cssPrestationFamiliale'])!.value,
      ipmPatronale: this.editForm.get(['ipmPatronale'])!.value,
      contributionForfaitaire: this.editForm.get(['contributionForfaitaire'])!.value,
      primeImposable: this.editForm.get(['primeImposable'])!.value,
      primeNonImposable: this.editForm.get(['primeNonImposable'])!.value,
      avantage: this.editForm.get(['avantage'])!.value,
      recapLigne: this.editForm.get(['recapLigne'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecap>>): void {
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
