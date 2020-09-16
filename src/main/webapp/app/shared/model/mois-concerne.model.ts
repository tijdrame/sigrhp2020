export interface IMoisConcerne {
  id?: number;
  libelle?: string;
  code?: string;
}

export class MoisConcerne implements IMoisConcerne {
  constructor(public id?: number, public libelle?: string, public code?: string) {}
}
