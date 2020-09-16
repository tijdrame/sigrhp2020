import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { MotifUpdateComponent } from 'app/entities/motif/motif-update.component';
import { MotifService } from 'app/entities/motif/motif.service';
import { Motif } from 'app/shared/model/motif.model';

describe('Component Tests', () => {
  describe('Motif Management Update Component', () => {
    let comp: MotifUpdateComponent;
    let fixture: ComponentFixture<MotifUpdateComponent>;
    let service: MotifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [MotifUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MotifUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotifUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotifService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Motif(123);
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
        const entity = new Motif();
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
