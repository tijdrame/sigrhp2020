import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'structure',
        loadChildren: () => import('./structure/structure.module').then(m => m.SigrhpStructureModule),
      },
      {
        path: 'convention',
        loadChildren: () => import('./convention/convention.module').then(m => m.SigrhpConventionModule),
      },
      {
        path: 'avantage',
        loadChildren: () => import('./avantage/avantage.module').then(m => m.SigrhpAvantageModule),
      },
      {
        path: 'motif',
        loadChildren: () => import('./motif/motif.module').then(m => m.SigrhpMotifModule),
      },
      {
        path: 'bareme',
        loadChildren: () => import('./bareme/bareme.module').then(m => m.SigrhpBaremeModule),
      },
      {
        path: 'exercice',
        loadChildren: () => import('./exercice/exercice.module').then(m => m.SigrhpExerciceModule),
      },
      {
        path: 'fonction',
        loadChildren: () => import('./fonction/fonction.module').then(m => m.SigrhpFonctionModule),
      },
      {
        path: 'mois-concerne',
        loadChildren: () => import('./mois-concerne/mois-concerne.module').then(m => m.SigrhpMoisConcerneModule),
      },
      {
        path: 'nationalite',
        loadChildren: () => import('./nationalite/nationalite.module').then(m => m.SigrhpNationaliteModule),
      },
      {
        path: 'prime',
        loadChildren: () => import('./prime/prime.module').then(m => m.SigrhpPrimeModule),
      },
      {
        path: 'regime',
        loadChildren: () => import('./regime/regime.module').then(m => m.SigrhpRegimeModule),
      },
      {
        path: 'sexe',
        loadChildren: () => import('./sexe/sexe.module').then(m => m.SigrhpSexeModule),
      },
      {
        path: 'situation-matrimoniale',
        loadChildren: () => import('./situation-matrimoniale/situation-matrimoniale.module').then(m => m.SigrhpSituationMatrimonialeModule),
      },
      {
        path: 'statut',
        loadChildren: () => import('./statut/statut.module').then(m => m.SigrhpStatutModule),
      },
      {
        path: 'statut-demande',
        loadChildren: () => import('./statut-demande/statut-demande.module').then(m => m.SigrhpStatutDemandeModule),
      },
      {
        path: 'type-absence',
        loadChildren: () => import('./type-absence/type-absence.module').then(m => m.SigrhpTypeAbsenceModule),
      },
      {
        path: 'type-contrat',
        loadChildren: () => import('./type-contrat/type-contrat.module').then(m => m.SigrhpTypeContratModule),
      },
      {
        path: 'type-paiement',
        loadChildren: () => import('./type-paiement/type-paiement.module').then(m => m.SigrhpTypePaiementModule),
      },
      {
        path: 'type-relation',
        loadChildren: () => import('./type-relation/type-relation.module').then(m => m.SigrhpTypeRelationModule),
      },
      {
        path: 'user-extra',
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.SigrhpUserExtraModule),
      },
      {
        path: 'collaborateur',
        loadChildren: () => import('./collaborateur/collaborateur.module').then(m => m.SigrhpCollaborateurModule),
      },
      {
        path: 'categorie',
        loadChildren: () => import('./categorie/categorie.module').then(m => m.SigrhpCategorieModule),
      },
      {
        path: 'avantage-collab',
        loadChildren: () => import('./avantage-collab/avantage-collab.module').then(m => m.SigrhpAvantageCollabModule),
      },
      {
        path: 'absence',
        loadChildren: () => import('./absence/absence.module').then(m => m.SigrhpAbsenceModule),
      },
      {
        path: 'remboursement',
        loadChildren: () => import('./remboursement/remboursement.module').then(m => m.SigrhpRemboursementModule),
      },
      {
        path: 'bulletin',
        loadChildren: () => import('./bulletin/bulletin.module').then(m => m.SigrhpBulletinModule),
      },
      {
        path: 'detail-pret',
        loadChildren: () => import('./detail-pret/detail-pret.module').then(m => m.SigrhpDetailPretModule),
      },
      {
        path: 'pret',
        loadChildren: () => import('./pret/pret.module').then(m => m.SigrhpPretModule),
      },
      {
        path: 'demande-conge',
        loadChildren: () => import('./demande-conge/demande-conge.module').then(m => m.SigrhpDemandeCongeModule),
      },
      {
        path: 'membre-famille',
        loadChildren: () => import('./membre-famille/membre-famille.module').then(m => m.SigrhpMembreFamilleModule),
      },
      {
        path: 'pieces',
        loadChildren: () => import('./pieces/pieces.module').then(m => m.SigrhpPiecesModule),
      },
      {
        path: 'prime-collab',
        loadChildren: () => import('./prime-collab/prime-collab.module').then(m => m.SigrhpPrimeCollabModule),
      },
      {
        path: 'recap',
        loadChildren: () => import('./recap/recap.module').then(m => m.SigrhpRecapModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SigrhpEntityModule {}
