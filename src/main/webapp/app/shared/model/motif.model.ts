import { IStructure } from 'app/shared/model/structure.model';

export interface IMotif {
  id?: number;
  libelle?: string;
  code?: string;
  structure?: IStructure;
}

export class Motif implements IMotif {
  constructor(public id?: number, public libelle?: string, public code?: string, public structure?: IStructure) {}
}
