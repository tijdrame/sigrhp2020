import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { IPrime } from 'app/shared/model/prime.model';

export interface IPrimeCollab {
  id?: number;
  montant?: number;
  collaborateur?: ICollaborateur;
  prime?: IPrime;
}

export class PrimeCollab implements IPrimeCollab {
  constructor(public id?: number, public montant?: number, public collaborateur?: ICollaborateur, public prime?: IPrime) {}
}
