import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { IPret } from 'app/shared/model/pret.model';

export interface IDetailPret {
  id?: number;
  montant?: number;
  isRembourse?: boolean;
  collaborateur?: ICollaborateur;
  pret?: IPret;
}

export class DetailPret implements IDetailPret {
  constructor(
    public id?: number,
    public montant?: number,
    public isRembourse?: boolean,
    public collaborateur?: ICollaborateur,
    public pret?: IPret
  ) {
    this.isRembourse = this.isRembourse || false;
  }
}
