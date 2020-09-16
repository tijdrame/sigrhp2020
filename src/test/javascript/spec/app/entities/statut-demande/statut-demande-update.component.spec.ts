import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { StatutDemandeUpdateComponent } from 'app/entities/statut-demande/statut-demande-update.component';
import { StatutDemandeService } from 'app/entities/statut-demande/statut-demande.service';
import { StatutDemande } from 'app/shared/model/statut-demande.model';

describe('Component Tests', () => {
  describe('StatutDemande Management Update Component', () => {
    let comp: StatutDemandeUpdateComponent;
    let fixture: ComponentFixture<StatutDemandeUpdateComponent>;
    let service: StatutDemandeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [StatutDemandeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StatutDemandeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatutDemandeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatutDemandeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatutDemande(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatutDemande();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
