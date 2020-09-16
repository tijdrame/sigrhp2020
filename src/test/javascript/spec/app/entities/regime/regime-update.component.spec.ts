import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { RegimeUpdateComponent } from 'app/entities/regime/regime-update.component';
import { RegimeService } from 'app/entities/regime/regime.service';
import { Regime } from 'app/shared/model/regime.model';

describe('Component Tests', () => {
  describe('Regime Management Update Component', () => {
    let comp: RegimeUpdateComponent;
    let fixture: ComponentFixture<RegimeUpdateComponent>;
    let service: RegimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [RegimeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RegimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegimeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegimeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Regime(123);
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
        const entity = new Regime();
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
