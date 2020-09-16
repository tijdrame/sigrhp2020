export interface IConvention {
  id?: number;
  libelle?: string;
  code?: string;
}

export class Convention implements IConvention {
  constructor(public id?: number, public libelle?: string, public code?: string) {}
}
