import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PrimeUpdateComponent } from 'app/entities/prime/prime-update.component';
import { PrimeService } from 'app/entities/prime/prime.service';
import { Prime } from 'app/shared/model/prime.model';

describe('Component Tests', () => {
  describe('Prime Management Update Component', () => {
    let comp: PrimeUpdateComponent;
    let fixture: ComponentFixture<PrimeUpdateComponent>;
    let service: PrimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PrimeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PrimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrimeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrimeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Prime(123);
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
        const entity = new Prime();
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
