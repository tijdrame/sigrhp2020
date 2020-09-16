import { IStructure } from 'app/shared/model/structure.model';

export interface IStatut {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class Statut implements IStatut {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
