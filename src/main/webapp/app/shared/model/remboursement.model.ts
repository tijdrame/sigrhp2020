import { Moment } from 'moment';
import { IDetailPret } from 'app/shared/model/detail-pret.model';
import { IBulletin } from 'app/shared/model/bulletin.model';

export interface IRemboursement {
  id?: number;
  dateRemboursement?: Moment;
  montant?: number;
  isRembourse?: boolean;
  detailPret?: IDetailPret;
  bulletins?: IBulletin[];
}

export class Remboursement implements IRemboursement {
  constructor(
    public id?: number,
    public dateRemboursement?: Moment,
    public montant?: number,
    public isRembourse?: boolean,
    public detailPret?: IDetailPret,
    public bulletins?: IBulletin[]
  ) {
    this.isRembourse = this.isRembourse || false;
  }
}
