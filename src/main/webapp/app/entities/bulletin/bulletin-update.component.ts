import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBulletin, Bulletin } from 'app/shared/model/bulletin.model';
import { BulletinService } from './bulletin.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { ITypePaiement } from 'app/shared/model/type-paiement.model';
import { TypePaiementService } from 'app/entities/type-paiement/type-paiement.service';
import { IRemboursement } from 'app/shared/model/remboursement.model';
import { RemboursementService } from 'app/entities/remboursement/remboursement.service';
import { IExercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from 'app/entities/exercice/exercice.service';
import { IMoisConcerne } from 'app/shared/model/mois-concerne.model';
import { MoisConcerneService } from 'app/entities/mois-concerne/mois-concerne.service';

type SelectableEntity = ICollaborateur | ITypePaiement | IRemboursement | IExercice | IMoisConcerne;

@Component({
  selector: 'jhi-bulletin-update',
  templateUrl: './bulletin-update.component.html',
})
export class BulletinUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  typepaiements: ITypePaiement[] = [];
  remboursements: IRemboursement[] = [];
  exercices: IExercice[] = [];
  moisconcernes: IMoisConcerne[] = [];

  editForm = this.fb.group({
    id: [],
    retenueIpm: [],
    retenuePharmacie: [],
    autreRetenue: [],
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
    nbPart: [],
    nbFemmes: [],
    nbEnfants: [],
    primeImposable: [],
    primeNonImposable: [],
    avantage: [],
    collaborateur: [],
    typePaiement: [null, Validators.required],
    remboursements: [],
    exercice: [null, Validators.required],
    moisConcerne: [],
  });

  constructor(
    protected bulletinService: BulletinService,
    protected collaborateurService: CollaborateurService,
    protected typePaiementService: TypePaiementService,
    protected remboursementService: RemboursementService,
    protected exerciceService: ExerciceService,
    protected moisConcerneService: MoisConcerneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bulletin }) => {
      this.updateForm(bulletin);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.typePaiementService.query().subscribe((res: HttpResponse<ITypePaiement[]>) => (this.typepaiements = res.body || []));

      this.remboursementService.query().subscribe((res: HttpResponse<IRemboursement[]>) => (this.remboursements = res.body || []));

      this.exerciceService.query().subscribe((res: HttpResponse<IExercice[]>) => (this.exercices = res.body || []));

      this.moisConcerneService.query().subscribe((res: HttpResponse<IMoisConcerne[]>) => (this.moisconcernes = res.body || []));
    });
  }

  updateForm(bulletin: IBulletin): void {
    this.editForm.patchValue({
      id: bulletin.id,
      retenueIpm: bulletin.retenueIpm,
      retenuePharmacie: bulletin.retenuePharmacie,
      autreRetenue: bulletin.autreRetenue,
      brutFiscal: bulletin.brutFiscal,
      netAPayer: bulletin.netAPayer,
      salaireBrutMensuel: bulletin.salaireBrutMensuel,
      impotSurRevenu: bulletin.impotSurRevenu,
      trimf: bulletin.trimf,
      ipresPartSalariale: bulletin.ipresPartSalariale,
      totRetenue: bulletin.totRetenue,
      ipresPartPatronales: bulletin.ipresPartPatronales,
      cssAccidentDeTravail: bulletin.cssAccidentDeTravail,
      cssPrestationFamiliale: bulletin.cssPrestationFamiliale,
      ipmPatronale: bulletin.ipmPatronale,
      contributionForfaitaire: bulletin.contributionForfaitaire,
      nbPart: bulletin.nbPart,
      nbFemmes: bulletin.nbFemmes,
      nbEnfants: bulletin.nbEnfants,
      primeImposable: bulletin.primeImposable,
      primeNonImposable: bulletin.primeNonImposable,
      avantage: bulletin.avantage,
      collaborateur: bulletin.collaborateur,
      typePaiement: bulletin.typePaiement,
      remboursements: bulletin.remboursements,
      exercice: bulletin.exercice,
      moisConcerne: bulletin.moisConcerne,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bulletin = this.createFromForm();
    if (bulletin.id !== undefined) {
      this.subscribeToSaveResponse(this.bulletinService.update(bulletin));
    } else {
      this.subscribeToSaveResponse(this.bulletinService.create(bulletin));
    }
  }

  private createFromForm(): IBulletin {
    return {
      ...new Bulletin(),
      id: this.editForm.get(['id'])!.value,
      retenueIpm: this.editForm.get(['retenueIpm'])!.value,
      retenuePharmacie: this.editForm.get(['retenuePharmacie'])!.value,
      autreRetenue: this.editForm.get(['autreRetenue'])!.value,
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
      nbPart: this.editForm.get(['nbPart'])!.value,
      nbFemmes: this.editForm.get(['nbFemmes'])!.value,
      nbEnfants: this.editForm.get(['nbEnfants'])!.value,
      primeImposable: this.editForm.get(['primeImposable'])!.value,
      primeNonImposable: this.editForm.get(['primeNonImposable'])!.value,
      avantage: this.editForm.get(['avantage'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      typePaiement: this.editForm.get(['typePaiement'])!.value,
      remboursements: this.editForm.get(['remboursements'])!.value,
      exercice: this.editForm.get(['exercice'])!.value,
      moisConcerne: this.editForm.get(['moisConcerne'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBulletin>>): void {
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

  getSelected(selectedVals: IRemboursement[], option: IRemboursement): IRemboursement {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
