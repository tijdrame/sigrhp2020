import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { RecapUpdateComponent } from 'app/entities/recap/recap-update.component';
import { RecapService } from 'app/entities/recap/recap.service';
import { Recap } from 'app/shared/model/recap.model';

describe('Component Tests', () => {
  describe('Recap Management Update Component', () => {
    let comp: RecapUpdateComponent;
    let fixture: ComponentFixture<RecapUpdateComponent>;
    let service: RecapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [RecapUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecapUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecapService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Recap(123);
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
        const entity = new Recap();
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
