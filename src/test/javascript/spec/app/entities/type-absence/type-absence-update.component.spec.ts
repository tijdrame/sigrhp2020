import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypeAbsenceUpdateComponent } from 'app/entities/type-absence/type-absence-update.component';
import { TypeAbsenceService } from 'app/entities/type-absence/type-absence.service';
import { TypeAbsence } from 'app/shared/model/type-absence.model';

describe('Component Tests', () => {
  describe('TypeAbsence Management Update Component', () => {
    let comp: TypeAbsenceUpdateComponent;
    let fixture: ComponentFixture<TypeAbsenceUpdateComponent>;
    let service: TypeAbsenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypeAbsenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeAbsenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeAbsenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeAbsenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeAbsence(123);
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
        const entity = new TypeAbsence();
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
