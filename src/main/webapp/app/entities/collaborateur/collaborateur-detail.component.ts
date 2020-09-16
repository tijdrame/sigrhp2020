import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICollaborateur } from 'app/shared/model/collaborateur.model';

@Component({
  selector: 'jhi-collaborateur-detail',
  templateUrl: './collaborateur-detail.component.html',
})
export class CollaborateurDetailComponent implements OnInit {
  collaborateur: ICollaborateur | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateur }) => (this.collaborateur = collaborateur));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
