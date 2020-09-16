import { IStructure } from 'app/shared/model/structure.model';

export interface ISituationMatrimoniale {
  id?: number;
  libelle?: string;
  code?: string;
  nbParts?: number;
  structure?: IStructure;
}

export class SituationMatrimoniale implements ISituationMatrimoniale {
  constructor(public id?: number, public libelle?: string, public code?: string, public nbParts?: number, public structure?: IStructure) {}
}
