import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISexe } from 'app/shared/model/sexe.model';

@Component({
  selector: 'jhi-sexe-detail',
  templateUrl: './sexe-detail.component.html',
})
export class SexeDetailComponent implements OnInit {
  sexe: ISexe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sexe }) => (this.sexe = sexe));
  }

  previousState(): void {
    window.history.back();
  }
}
