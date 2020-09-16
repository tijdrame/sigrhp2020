export interface INationalite {
  id?: number;
  libelle?: string;
}

export class Nationalite implements INationalite {
  constructor(public id?: number, public libelle?: string) {}
}
