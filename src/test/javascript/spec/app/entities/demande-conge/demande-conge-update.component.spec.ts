import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { DemandeCongeUpdateComponent } from 'app/entities/demande-conge/demande-conge-update.component';
import { DemandeCongeService } from 'app/entities/demande-conge/demande-conge.service';
import { DemandeConge } from 'app/shared/model/demande-conge.model';

describe('Component Tests', () => {
  describe('DemandeConge Management Update Component', () => {
    let comp: DemandeCongeUpdateComponent;
    let fixture: ComponentFixture<DemandeCongeUpdateComponent>;
    let service: DemandeCongeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [DemandeCongeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DemandeCongeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandeCongeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DemandeCongeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DemandeConge(123);
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
        const entity = new DemandeConge();
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
