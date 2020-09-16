import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { StatutUpdateComponent } from 'app/entities/statut/statut-update.component';
import { StatutService } from 'app/entities/statut/statut.service';
import { Statut } from 'app/shared/model/statut.model';

describe('Component Tests', () => {
  describe('Statut Management Update Component', () => {
    let comp: StatutUpdateComponent;
    let fixture: ComponentFixture<StatutUpdateComponent>;
    let service: StatutService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [StatutUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StatutUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatutUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatutService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Statut(123);
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
        const entity = new Statut();
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
