import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypeRelationUpdateComponent } from 'app/entities/type-relation/type-relation-update.component';
import { TypeRelationService } from 'app/entities/type-relation/type-relation.service';
import { TypeRelation } from 'app/shared/model/type-relation.model';

describe('Component Tests', () => {
  describe('TypeRelation Management Update Component', () => {
    let comp: TypeRelationUpdateComponent;
    let fixture: ComponentFixture<TypeRelationUpdateComponent>;
    let service: TypeRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypeRelationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeRelationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeRelation(123);
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
        const entity = new TypeRelation();
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
