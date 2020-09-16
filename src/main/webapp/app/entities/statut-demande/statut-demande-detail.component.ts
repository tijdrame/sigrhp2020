import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatutDemande } from 'app/shared/model/statut-demande.model';

@Component({
  selector: 'jhi-statut-demande-detail',
  templateUrl: './statut-demande-detail.component.html',
})
export class StatutDemandeDetailComponent implements OnInit {
  statutDemande: IStatutDemande | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statutDemande }) => (this.statutDemande = statutDemande));
  }

  previousState(): void {
    window.history.back();
  }
}
