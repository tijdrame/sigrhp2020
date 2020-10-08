import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICollaborateur, Collaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from './collaborateur.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IFonction } from 'app/shared/model/fonction.model';
import { FonctionService } from 'app/entities/fonction/fonction.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';
import { INationalite } from 'app/shared/model/nationalite.model';
import { NationaliteService } from 'app/entities/nationalite/nationalite.service';
import { IStatut } from 'app/shared/model/statut.model';
import { StatutService } from 'app/entities/statut/statut.service';
import { ISituationMatrimoniale } from 'app/shared/model/situation-matrimoniale.model';
import { SituationMatrimonialeService } from 'app/entities/situation-matrimoniale/situation-matrimoniale.service';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/type-contrat.service';
import { IRegime } from 'app/shared/model/regime.model';
import { RegimeService } from 'app/entities/regime/regime.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISexe } from 'app/shared/model/sexe.model';
import { SexeService } from 'app/entities/sexe/sexe.service';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure/structure.service';

type SelectableEntity =
  | IFonction
  | ICategorie
  | INationalite
  | IStatut
  | ISituationMatrimoniale
  | ITypeContrat
  | IRegime
  | IUser
  | ISexe
  | IStructure;

@Component({
  selector: 'jhi-collaborateur-update',
  templateUrl: './collaborateur-update.component.html',
})
export class CollaborateurUpdateComponent implements OnInit {
  isSaving = false;
  fonctions: IFonction[] = [];
  categories: ICategorie[] = [];
  nationalites: INationalite[] = [];
  statuts: IStatut[] = [];
  situationmatrimoniales: ISituationMatrimoniale[] = [];
  typecontrats: ITypeContrat[] = [];
  regimes: IRegime[] = [];
  users: IUser[] = [];
  sexes: ISexe[] = [];
  structures: IStructure[] = [];
  dateNaissanceDp: any;

  editForm = this.fb.group({
    id: [],
    prenom: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    matricule: [null, []],
    adresse: [null, [Validators.required]],
    tauxHoraire: [null, [Validators.required]],
    salaireDeBase: [null, [Validators.required]],
    surSalaire: [null, [Validators.required]],
    retenueRepas: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    login: [null, [Validators.required]],
    email: [null, []],
    primeTransport: [null, [Validators.required]],
    telephone: [null, [Validators.required]],
    numeroRib: [],
    fonction: [],
    categorie: [],
    nationalite: [],
    statut: [],
    situationMatrimoniale: [],
    typeContrat: [],
    regime: [],
    userCollab: [],
    sexe: [],
    structure: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected collaborateurService: CollaborateurService,
    protected fonctionService: FonctionService,
    protected categorieService: CategorieService,
    protected nationaliteService: NationaliteService,
    protected statutService: StatutService,
    protected situationMatrimonialeService: SituationMatrimonialeService,
    protected typeContratService: TypeContratService,
    protected regimeService: RegimeService,
    protected userService: UserService,
    protected sexeService: SexeService,
    protected structureService: StructureService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateur }) => {
      this.updateForm(collaborateur);

      this.fonctionService.query().subscribe((res: HttpResponse<IFonction[]>) => (this.fonctions = res.body || []));

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));

      this.nationaliteService.queryBis().subscribe((res: HttpResponse<INationalite[]>) => (this.nationalites = res.body || []));

      this.statutService.query().subscribe((res: HttpResponse<IStatut[]>) => (this.statuts = res.body || []));

      this.situationMatrimonialeService
        .query()
        .subscribe((res: HttpResponse<ISituationMatrimoniale[]>) => (this.situationmatrimoniales = res.body || []));

      this.typeContratService.query().subscribe((res: HttpResponse<ITypeContrat[]>) => (this.typecontrats = res.body || []));

      this.regimeService.query().subscribe((res: HttpResponse<IRegime[]>) => (this.regimes = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.sexeService.query().subscribe((res: HttpResponse<ISexe[]>) => (this.sexes = res.body || []));

      this.structureService.query().subscribe((res: HttpResponse<IStructure[]>) => (this.structures = res.body || []));
    });
  }

  updateForm(collaborateur: ICollaborateur): void {
    this.editForm.patchValue({
      id: collaborateur.id,
      prenom: collaborateur.prenom,
      nom: collaborateur.nom,
      matricule: collaborateur.matricule,
      adresse: collaborateur.adresse,
      tauxHoraire: collaborateur.tauxHoraire,
      salaireDeBase: collaborateur.salaireDeBase,
      surSalaire: collaborateur.surSalaire,
      retenueRepas: collaborateur.retenueRepas,
      dateNaissance: collaborateur.dateNaissance,
      photo: collaborateur.photo,
      photoContentType: collaborateur.photoContentType,
      login: collaborateur.login,
      email: collaborateur.email,
      primeTransport: collaborateur.primeTransport,
      telephone: collaborateur.telephone,
      numeroRib: collaborateur.numeroRib,
      fonction: collaborateur.fonction,
      categorie: collaborateur.categorie,
      nationalite: collaborateur.nationalite,
      statut: collaborateur.statut,
      situationMatrimoniale: collaborateur.situationMatrimoniale,
      typeContrat: collaborateur.typeContrat,
      regime: collaborateur.regime,
      userCollab: collaborateur.userCollab,
      sexe: collaborateur.sexe,
      structure: collaborateur.structure,
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
    const collaborateur = this.createFromForm();
    if (collaborateur.id !== undefined) {
      this.subscribeToSaveResponse(this.collaborateurService.update(collaborateur));
    } else {
      this.subscribeToSaveResponse(this.collaborateurService.create(collaborateur));
    }
  }

  private createFromForm(): ICollaborateur {
    return {
      ...new Collaborateur(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      tauxHoraire: this.editForm.get(['tauxHoraire'])!.value,
      salaireDeBase: this.editForm.get(['salaireDeBase'])!.value,
      surSalaire: this.editForm.get(['surSalaire'])!.value,
      retenueRepas: this.editForm.get(['retenueRepas'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      login: this.editForm.get(['login'])!.value,
      email: this.editForm.get(['email'])!.value,
      primeTransport: this.editForm.get(['primeTransport'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      numeroRib: this.editForm.get(['numeroRib'])!.value,
      fonction: this.editForm.get(['fonction'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      nationalite: this.editForm.get(['nationalite'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      situationMatrimoniale: this.editForm.get(['situationMatrimoniale'])!.value,
      typeContrat: this.editForm.get(['typeContrat'])!.value,
      regime: this.editForm.get(['regime'])!.value,
      userCollab: this.editForm.get(['userCollab'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      structure: this.editForm.get(['structure'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollaborateur>>): void {
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
