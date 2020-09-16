import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { ITypePaiement } from 'app/shared/model/type-paiement.model';
import { IRemboursement } from 'app/shared/model/remboursement.model';
import { IExercice } from 'app/shared/model/exercice.model';
import { IMoisConcerne } from 'app/shared/model/mois-concerne.model';

export interface IBulletin {
  id?: number;
  retenueIpm?: number;
  retenuePharmacie?: number;
  autreRetenue?: number;
  brutFiscal?: number;
  netAPayer?: number;
  salaireBrutMensuel?: number;
  impotSurRevenu?: number;
  trimf?: number;
  ipresPartSalariale?: number;
  totRetenue?: number;
  ipresPartPatronales?: number;
  cssAccidentDeTravail?: number;
  cssPrestationFamiliale?: number;
  ipmPatronale?: number;
  contributionForfaitaire?: number;
  nbPart?: number;
  nbFemmes?: number;
  nbEnfants?: number;
  primeImposable?: number;
  primeNonImposable?: number;
  avantage?: number;
  collaborateur?: ICollaborateur;
  typePaiement?: ITypePaiement;
  remboursements?: IRemboursement[];
  exercice?: IExercice;
  moisConcerne?: IMoisConcerne;
}

export class Bulletin implements IBulletin {
  constructor(
    public id?: number,
    public retenueIpm?: number,
    public retenuePharmacie?: number,
    public autreRetenue?: number,
    public brutFiscal?: number,
    public netAPayer?: number,
    public salaireBrutMensuel?: number,
    public impotSurRevenu?: number,
    public trimf?: number,
    public ipresPartSalariale?: number,
    public totRetenue?: number,
    public ipresPartPatronales?: number,
    public cssAccidentDeTravail?: number,
    public cssPrestationFamiliale?: number,
    public ipmPatronale?: number,
    public contributionForfaitaire?: number,
    public nbPart?: number,
    public nbFemmes?: number,
    public nbEnfants?: number,
    public primeImposable?: number,
    public primeNonImposable?: number,
    public avantage?: number,
    public collaborateur?: ICollaborateur,
    public typePaiement?: ITypePaiement,
    public remboursements?: IRemboursement[],
    public exercice?: IExercice,
    public moisConcerne?: IMoisConcerne
  ) {}
}
