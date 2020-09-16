import { IStructure } from 'app/shared/model/structure.model';

export interface ITypePaiement {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class TypePaiement implements ITypePaiement {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
