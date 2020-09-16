import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvantageCollab } from 'app/shared/model/avantage-collab.model';

@Component({
  selector: 'jhi-avantage-collab-detail',
  templateUrl: './avantage-collab-detail.component.html',
})
export class AvantageCollabDetailComponent implements OnInit {
  avantageCollab: IAvantageCollab | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avantageCollab }) => (this.avantageCollab = avantageCollab));
  }

  previousState(): void {
    window.history.back();
  }
}
