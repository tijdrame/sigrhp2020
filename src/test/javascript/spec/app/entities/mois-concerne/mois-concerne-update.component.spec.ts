import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { MoisConcerneUpdateComponent } from 'app/entities/mois-concerne/mois-concerne-update.component';
import { MoisConcerneService } from 'app/entities/mois-concerne/mois-concerne.service';
import { MoisConcerne } from 'app/shared/model/mois-concerne.model';

describe('Component Tests', () => {
  describe('MoisConcerne Management Update Component', () => {
    let comp: MoisConcerneUpdateComponent;
    let fixture: ComponentFixture<MoisConcerneUpdateComponent>;
    let service: MoisConcerneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [MoisConcerneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MoisConcerneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MoisConcerneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MoisConcerneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MoisConcerne(123);
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
        const entity = new MoisConcerne();
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
