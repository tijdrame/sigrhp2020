import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBareme } from 'app/shared/model/bareme.model';

@Component({
  selector: 'jhi-bareme-detail',
  templateUrl: './bareme-detail.component.html',
})
export class BaremeDetailComponent implements OnInit {
  bareme: IBareme | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bareme }) => (this.bareme = bareme));
  }

  previousState(): void {
    window.history.back();
  }
}
