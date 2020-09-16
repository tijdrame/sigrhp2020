import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMembreFamille } from 'app/shared/model/membre-famille.model';

@Component({
  selector: 'jhi-membre-famille-detail',
  templateUrl: './membre-famille-detail.component.html',
})
export class MembreFamilleDetailComponent implements OnInit {
  membreFamille: IMembreFamille | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreFamille }) => (this.membreFamille = membreFamille));
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
