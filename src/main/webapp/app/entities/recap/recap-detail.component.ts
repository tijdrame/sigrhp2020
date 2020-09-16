import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecap } from 'app/shared/model/recap.model';

@Component({
  selector: 'jhi-recap-detail',
  templateUrl: './recap-detail.component.html',
})
export class RecapDetailComponent implements OnInit {
  recap: IRecap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recap }) => (this.recap = recap));
  }

  previousState(): void {
    window.history.back();
  }
}
