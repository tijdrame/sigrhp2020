import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INationalite } from 'app/shared/model/nationalite.model';

@Component({
  selector: 'jhi-nationalite-detail',
  templateUrl: './nationalite-detail.component.html',
})
export class NationaliteDetailComponent implements OnInit {
  nationalite: INationalite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nationalite }) => (this.nationalite = nationalite));
  }

  previousState(): void {
    window.history.back();
  }
}
