import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatut } from 'app/shared/model/statut.model';

@Component({
  selector: 'jhi-statut-detail',
  templateUrl: './statut-detail.component.html',
})
export class StatutDetailComponent implements OnInit {
  statut: IStatut | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statut }) => (this.statut = statut));
  }

  previousState(): void {
    window.history.back();
  }
}
