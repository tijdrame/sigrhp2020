import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PretUpdateComponent } from 'app/entities/pret/pret-update.component';
import { PretService } from 'app/entities/pret/pret.service';
import { Pret } from 'app/shared/model/pret.model';

describe('Component Tests', () => {
  describe('Pret Management Update Component', () => {
    let comp: PretUpdateComponent;
    let fixture: ComponentFixture<PretUpdateComponent>;
    let service: PretService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PretUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PretUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PretUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PretService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pret(123);
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
        const entity = new Pret();
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
