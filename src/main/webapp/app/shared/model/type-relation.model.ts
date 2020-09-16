export interface ITypeRelation {
  id?: number;
  libelle?: string;
  code?: string;
  nbParts?: number;
}

export class TypeRelation implements ITypeRelation {
  constructor(public id?: number, public libelle?: string, public code?: string, public nbParts?: number) {}
}
