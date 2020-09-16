import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoisConcerne } from 'app/shared/model/mois-concerne.model';

@Component({
  selector: 'jhi-mois-concerne-detail',
  templateUrl: './mois-concerne-detail.component.html',
})
export class MoisConcerneDetailComponent implements OnInit {
  moisConcerne: IMoisConcerne | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moisConcerne }) => (this.moisConcerne = moisConcerne));
  }

  previousState(): void {
    window.history.back();
  }
}
