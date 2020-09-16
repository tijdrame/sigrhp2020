import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { ExerciceUpdateComponent } from 'app/entities/exercice/exercice-update.component';
import { ExerciceService } from 'app/entities/exercice/exercice.service';
import { Exercice } from 'app/shared/model/exercice.model';

describe('Component Tests', () => {
  describe('Exercice Management Update Component', () => {
    let comp: ExerciceUpdateComponent;
    let fixture: ComponentFixture<ExerciceUpdateComponent>;
    let service: ExerciceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [ExerciceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExerciceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExerciceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExerciceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Exercice(123);
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
        const entity = new Exercice();
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
