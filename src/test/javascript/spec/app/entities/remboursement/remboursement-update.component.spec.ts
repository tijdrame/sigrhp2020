import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { RemboursementUpdateComponent } from 'app/entities/remboursement/remboursement-update.component';
import { RemboursementService } from 'app/entities/remboursement/remboursement.service';
import { Remboursement } from 'app/shared/model/remboursement.model';

describe('Component Tests', () => {
  describe('Remboursement Management Update Component', () => {
    let comp: RemboursementUpdateComponent;
    let fixture: ComponentFixture<RemboursementUpdateComponent>;
    let service: RemboursementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [RemboursementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RemboursementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemboursementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemboursementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remboursement(123);
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
        const entity = new Remboursement();
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
