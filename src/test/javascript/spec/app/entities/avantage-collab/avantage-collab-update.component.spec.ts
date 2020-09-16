import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { AvantageCollabUpdateComponent } from 'app/entities/avantage-collab/avantage-collab-update.component';
import { AvantageCollabService } from 'app/entities/avantage-collab/avantage-collab.service';
import { AvantageCollab } from 'app/shared/model/avantage-collab.model';

describe('Component Tests', () => {
  describe('AvantageCollab Management Update Component', () => {
    let comp: AvantageCollabUpdateComponent;
    let fixture: ComponentFixture<AvantageCollabUpdateComponent>;
    let service: AvantageCollabService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [AvantageCollabUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvantageCollabUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvantageCollabUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvantageCollabService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvantageCollab(123);
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
        const entity = new AvantageCollab();
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
