import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAbsence } from 'app/shared/model/type-absence.model';

@Component({
  selector: 'jhi-type-absence-detail',
  templateUrl: './type-absence-detail.component.html',
})
export class TypeAbsenceDetailComponent implements OnInit {
  typeAbsence: ITypeAbsence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAbsence }) => (this.typeAbsence = typeAbsence));
  }

  previousState(): void {
    window.history.back();
  }
}
