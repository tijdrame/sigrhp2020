export interface ITypeAbsence {
  id?: number;
  libelle?: string;
  code?: string;
}

export class TypeAbsence implements ITypeAbsence {
  constructor(public id?: number, public libelle?: string, public code?: string) {}
}
