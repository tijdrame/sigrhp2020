import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { ConventionUpdateComponent } from 'app/entities/convention/convention-update.component';
import { ConventionService } from 'app/entities/convention/convention.service';
import { Convention } from 'app/shared/model/convention.model';

describe('Component Tests', () => {
  describe('Convention Management Update Component', () => {
    let comp: ConventionUpdateComponent;
    let fixture: ComponentFixture<ConventionUpdateComponent>;
    let service: ConventionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [ConventionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConventionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConventionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConventionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Convention(123);
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
        const entity = new Convention();
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
