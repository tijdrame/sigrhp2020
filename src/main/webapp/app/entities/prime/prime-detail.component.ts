import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrime } from 'app/shared/model/prime.model';

@Component({
  selector: 'jhi-prime-detail',
  templateUrl: './prime-detail.component.html',
})
export class PrimeDetailComponent implements OnInit {
  prime: IPrime | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prime }) => (this.prime = prime));
  }

  previousState(): void {
    window.history.back();
  }
}
