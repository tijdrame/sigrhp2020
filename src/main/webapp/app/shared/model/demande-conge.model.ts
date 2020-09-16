import { Moment } from 'moment';
import { IStatutDemande } from 'app/shared/model/statut-demande.model';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { ITypeAbsence } from 'app/shared/model/type-absence.model';

export interface IDemandeConge {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  motifRejet?: string;
  libelle?: string;
  statutRH?: IStatutDemande;
  statutDG?: IStatutDemande;
  collaborateur?: ICollaborateur;
  typeAbsence?: ITypeAbsence;
}

export class DemandeConge implements IDemandeConge {
  constructor(
    public id?: number,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public motifRejet?: string,
    public libelle?: string,
    public statutRH?: IStatutDemande,
    public statutDG?: IStatutDemande,
    public collaborateur?: ICollaborateur,
    public typeAbsence?: ITypeAbsence
  ) {}
}
