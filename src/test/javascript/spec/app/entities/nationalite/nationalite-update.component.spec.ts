import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { NationaliteUpdateComponent } from 'app/entities/nationalite/nationalite-update.component';
import { NationaliteService } from 'app/entities/nationalite/nationalite.service';
import { Nationalite } from 'app/shared/model/nationalite.model';

describe('Component Tests', () => {
  describe('Nationalite Management Update Component', () => {
    let comp: NationaliteUpdateComponent;
    let fixture: ComponentFixture<NationaliteUpdateComponent>;
    let service: NationaliteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [NationaliteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NationaliteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NationaliteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NationaliteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nationalite(123);
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
        const entity = new Nationalite();
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
