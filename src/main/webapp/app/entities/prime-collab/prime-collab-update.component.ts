import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPrimeCollab, PrimeCollab } from 'app/shared/model/prime-collab.model';
import { PrimeCollabService } from './prime-collab.service';
import { ICollaborateur } from 'app/shared/model/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/collaborateur.service';
import { IPrime } from 'app/shared/model/prime.model';
import { PrimeService } from 'app/entities/prime/prime.service';

type SelectableEntity = ICollaborateur | IPrime;

@Component({
  selector: 'jhi-prime-collab-update',
  templateUrl: './prime-collab-update.component.html',
})
export class PrimeCollabUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateur[] = [];
  primes: IPrime[] = [];

  editForm = this.fb.group({
    id: [],
    montant: [null, [Validators.required]],
    collaborateur: [null, Validators.required],
    prime: [null, Validators.required],
  });

  constructor(
    protected primeCollabService: PrimeCollabService,
    protected collaborateurService: CollaborateurService,
    protected primeService: PrimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ primeCollab }) => {
      this.updateForm(primeCollab);

      this.collaborateurService.query().subscribe((res: HttpResponse<ICollaborateur[]>) => (this.collaborateurs = res.body || []));

      this.primeService.query().subscribe((res: HttpResponse<IPrime[]>) => (this.primes = res.body || []));
    });
  }

  updateForm(primeCollab: IPrimeCollab): void {
    this.editForm.patchValue({
      id: primeCollab.id,
      montant: primeCollab.montant,
      collaborateur: primeCollab.collaborateur,
      prime: primeCollab.prime,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const primeCollab = this.createFromForm();
    if (primeCollab.id !== undefined) {
      this.subscribeToSaveResponse(this.primeCollabService.update(primeCollab));
    } else {
      this.subscribeToSaveResponse(this.primeCollabService.create(primeCollab));
    }
  }

  private createFromForm(): IPrimeCollab {
    return {
      ...new PrimeCollab(),
      id: this.editForm.get(['id'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      collaborateur: this.editForm.get(['collaborateur'])!.value,
      prime: this.editForm.get(['prime'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrimeCollab>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
