import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigrhpTestModule } from '../../../test.module';
import { TypeRelationDetailComponent } from 'app/entities/type-relation/type-relation-detail.component';
import { TypeRelation } from 'app/shared/model/type-relation.model';

describe('Component Tests', () => {
  describe('TypeRelation Management Detail Component', () => {
    let comp: TypeRelationDetailComponent;
    let fixture: ComponentFixture<TypeRelationDetailComponent>;
    const route = ({ data: of({ typeRelation: new TypeRelation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigrhpTestModule],
        declarations: [TypeRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeRelation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeRelation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
