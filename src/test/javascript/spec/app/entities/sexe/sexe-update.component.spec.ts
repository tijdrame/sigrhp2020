import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { SexeUpdateComponent } from 'app/entities/sexe/sexe-update.component';
import { SexeService } from 'app/entities/sexe/sexe.service';
import { Sexe } from 'app/shared/model/sexe.model';

describe('Component Tests', () => {
  describe('Sexe Management Update Component', () => {
    let comp: SexeUpdateComponent;
    let fixture: ComponentFixture<SexeUpdateComponent>;
    let service: SexeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [SexeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SexeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SexeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SexeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sexe(123);
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
        const entity = new Sexe();
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
