import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePaiement } from 'app/shared/model/type-paiement.model';

@Component({
  selector: 'jhi-type-paiement-detail',
  templateUrl: './type-paiement-detail.component.html',
})
export class TypePaiementDetailComponent implements OnInit {
  typePaiement: ITypePaiement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePaiement }) => (this.typePaiement = typePaiement));
  }

  previousState(): void {
    window.history.back();
  }
}
