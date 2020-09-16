import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeRelation } from 'app/shared/model/type-relation.model';

@Component({
  selector: 'jhi-type-relation-detail',
  templateUrl: './type-relation-detail.component.html',
})
export class TypeRelationDetailComponent implements OnInit {
  typeRelation: ITypeRelation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeRelation }) => (this.typeRelation = typeRelation));
  }

  previousState(): void {
    window.history.back();
  }
}
