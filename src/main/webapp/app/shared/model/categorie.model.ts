import { IStructure } from 'app/shared/model/structure.model';

export interface ICategorie {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
