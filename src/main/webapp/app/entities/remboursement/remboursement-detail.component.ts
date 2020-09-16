import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemboursement } from 'app/shared/model/remboursement.model';

@Component({
  selector: 'jhi-remboursement-detail',
  templateUrl: './remboursement-detail.component.html',
})
export class RemboursementDetailComponent implements OnInit {
  remboursement: IRemboursement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remboursement }) => (this.remboursement = remboursement));
  }

  previousState(): void {
    window.history.back();
  }
}
