import { Moment } from 'moment';
import { IStructure } from 'app/shared/model/structure.model';

export interface IPret {
  id?: number;
  libelle?: string;
  nbRemboursement?: number;
  datePret?: Moment;
  dateDebutRemboursement?: Moment;
  structure?: IStructure;
}

export class Pret implements IPret {
  constructor(
    public id?: number,
    public libelle?: string,
    public nbRemboursement?: number,
    public datePret?: Moment,
    public dateDebutRemboursement?: Moment,
    public structure?: IStructure
  ) {}
}
