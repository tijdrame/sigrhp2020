import { IStructure } from 'app/shared/model/structure.model';

export interface IPrime {
  id?: number;
  libelle?: string;
  code?: string;
  imposable?: boolean;
  structure?: IStructure;
}

export class Prime implements IPrime {
  constructor(
    public id?: number,
    public libelle?: string,
    public code?: string,
    public imposable?: boolean,
    public structure?: IStructure
  ) {
    this.imposable = this.imposable || false;
  }
}
