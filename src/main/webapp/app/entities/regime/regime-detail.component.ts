import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegime } from 'app/shared/model/regime.model';

@Component({
  selector: 'jhi-regime-detail',
  templateUrl: './regime-detail.component.html',
})
export class RegimeDetailComponent implements OnInit {
  regime: IRegime | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regime }) => (this.regime = regime));
  }

  previousState(): void {
    window.history.back();
  }
}
