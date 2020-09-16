import { IStructure } from 'app/shared/model/structure.model';

export interface IAvantage {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class Avantage implements IAvantage {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
