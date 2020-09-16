import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailPret } from 'app/shared/model/detail-pret.model';

@Component({
  selector: 'jhi-detail-pret-detail',
  templateUrl: './detail-pret-detail.component.html',
})
export class DetailPretDetailComponent implements OnInit {
  detailPret: IDetailPret | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailPret }) => (this.detailPret = detailPret));
  }

  previousState(): void {
    window.history.back();
  }
}
