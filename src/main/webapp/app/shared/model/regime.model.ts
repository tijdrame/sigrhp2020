import { IStructure } from 'app/shared/model/structure.model';

export interface IRegime {
  id?: number;
  libelle?: string;
  code?: string;
  tauxPatronal?: number;
  tauxSalarial?: number;
  plafond?: number;
  structure?: IStructure;
}

export class Regime implements IRegime {
  constructor(
    public id?: number,
    public libelle?: string,
    public code?: string,
    public tauxPatronal?: number,
    public tauxSalarial?: number,
    public plafond?: number,
    public structure?: IStructure
  ) {}
}
