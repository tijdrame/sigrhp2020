import { IStructure } from 'app/shared/model/structure.model';

export interface ITypeContrat {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class TypeContrat implements ITypeContrat {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
