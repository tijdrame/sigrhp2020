import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { BaremeUpdateComponent } from 'app/entities/bareme/bareme-update.component';
import { BaremeService } from 'app/entities/bareme/bareme.service';
import { Bareme } from 'app/shared/model/bareme.model';

describe('Component Tests', () => {
  describe('Bareme Management Update Component', () => {
    let comp: BaremeUpdateComponent;
    let fixture: ComponentFixture<BaremeUpdateComponent>;
    let service: BaremeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [BaremeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BaremeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BaremeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BaremeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bareme(123);
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
        const entity = new Bareme();
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
