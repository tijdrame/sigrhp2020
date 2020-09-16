import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvantage } from 'app/shared/model/avantage.model';

@Component({
  selector: 'jhi-avantage-detail',
  templateUrl: './avantage-detail.component.html',
})
export class AvantageDetailComponent implements OnInit {
  avantage: IAvantage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avantage }) => (this.avantage = avantage));
  }

  previousState(): void {
    window.history.back();
  }
}
