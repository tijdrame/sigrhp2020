import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { AvantageUpdateComponent } from 'app/entities/avantage/avantage-update.component';
import { AvantageService } from 'app/entities/avantage/avantage.service';
import { Avantage } from 'app/shared/model/avantage.model';

describe('Component Tests', () => {
  describe('Avantage Management Update Component', () => {
    let comp: AvantageUpdateComponent;
    let fixture: ComponentFixture<AvantageUpdateComponent>;
    let service: AvantageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [AvantageUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvantageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvantageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvantageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Avantage(123);
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
        const entity = new Avantage();
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
