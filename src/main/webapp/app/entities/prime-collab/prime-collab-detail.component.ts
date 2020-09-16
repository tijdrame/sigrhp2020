import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrimeCollab } from 'app/shared/model/prime-collab.model';

@Component({
  selector: 'jhi-prime-collab-detail',
  templateUrl: './prime-collab-detail.component.html',
})
export class PrimeCollabDetailComponent implements OnInit {
  primeCollab: IPrimeCollab | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ primeCollab }) => (this.primeCollab = primeCollab));
  }

  previousState(): void {
    window.history.back();
  }
}
