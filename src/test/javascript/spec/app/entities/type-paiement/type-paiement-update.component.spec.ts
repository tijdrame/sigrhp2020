import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypePaiementUpdateComponent } from 'app/entities/type-paiement/type-paiement-update.component';
import { TypePaiementService } from 'app/entities/type-paiement/type-paiement.service';
import { TypePaiement } from 'app/shared/model/type-paiement.model';

describe('Component Tests', () => {
  describe('TypePaiement Management Update Component', () => {
    let comp: TypePaiementUpdateComponent;
    let fixture: ComponentFixture<TypePaiementUpdateComponent>;
    let service: TypePaiementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypePaiementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypePaiementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePaiementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePaiementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePaiement(123);
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
        const entity = new TypePaiement();
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
