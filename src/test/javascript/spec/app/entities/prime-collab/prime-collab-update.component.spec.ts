import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { PrimeCollabUpdateComponent } from 'app/entities/prime-collab/prime-collab-update.component';
import { PrimeCollabService } from 'app/entities/prime-collab/prime-collab.service';
import { PrimeCollab } from 'app/shared/model/prime-collab.model';

describe('Component Tests', () => {
  describe('PrimeCollab Management Update Component', () => {
    let comp: PrimeCollabUpdateComponent;
    let fixture: ComponentFixture<PrimeCollabUpdateComponent>;
    let service: PrimeCollabService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [PrimeCollabUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PrimeCollabUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrimeCollabUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrimeCollabService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrimeCollab(123);
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
        const entity = new PrimeCollab();
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
