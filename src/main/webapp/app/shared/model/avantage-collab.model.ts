import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { IAvantage } from 'app/shared/model/avantage.model';

export interface IAvantageCollab {
  id?: number;
  montant?: number;
  collaborateur?: ICollaborateur;
  avantage?: IAvantage;
}

export class AvantageCollab implements IAvantageCollab {
  constructor(public id?: number, public montant?: number, public collaborateur?: ICollaborateur, public avantage?: IAvantage) {}
}
