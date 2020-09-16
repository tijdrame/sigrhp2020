import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypeContratUpdateComponent } from 'app/entities/type-contrat/type-contrat-update.component';
import { TypeContratService } from 'app/entities/type-contrat/type-contrat.service';
import { TypeContrat } from 'app/shared/model/type-contrat.model';

describe('Component Tests', () => {
  describe('TypeContrat Management Update Component', () => {
    let comp: TypeContratUpdateComponent;
    let fixture: ComponentFixture<TypeContratUpdateComponent>;
    let service: TypeContratService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypeContratUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeContratUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeContratUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeContratService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeContrat(123);
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
        const entity = new TypeContrat();
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
