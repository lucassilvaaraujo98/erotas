import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Caronas } from './caronas';

describe('Caronas', () => {
  let component: Caronas;
  let fixture: ComponentFixture<Caronas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Caronas],
    }).compileComponents();

    fixture = TestBed.createComponent(Caronas);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
