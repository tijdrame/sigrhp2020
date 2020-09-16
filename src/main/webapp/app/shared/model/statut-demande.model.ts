export interface IStatutDemande {
  id?: number;
  libelle?: string;
  code?: string;
}

export class StatutDemande implements IStatutDemande {
  constructor(public id?: number, public libelle?: string, public code?: string) {}
}
