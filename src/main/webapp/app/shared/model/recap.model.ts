import { ICollaborateur } from 'app/shared/model/collaborateur.model';

export interface IRecap {
  id?: number;
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
  primeImposable?: number;
  primeNonImposable?: number;
  avantage?: number;
  recapLigne?: number;
  collaborateur?: ICollaborateur;
}

export class Recap implements IRecap {
  constructor(
    public id?: number,
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
    public primeImposable?: number,
    public primeNonImposable?: number,
    public avantage?: number,
    public recapLigne?: number,
    public collaborateur?: ICollaborateur
  ) {}
}
