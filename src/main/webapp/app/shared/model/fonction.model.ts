import { IStructure } from 'app/shared/model/structure.model';

export interface IFonction {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class Fonction implements IFonction {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
