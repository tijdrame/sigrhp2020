export interface ISexe {
  id?: number;
  libelle?: string;
  code?: string;
}

export class Sexe implements ISexe {
  constructor(public id?: number, public libelle?: string, public code?: string) {}
}
