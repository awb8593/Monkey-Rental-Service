import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonkeySearchComponent } from './monkey-search.component';

describe('MonkeySearchComponent', () => {
  let component: MonkeySearchComponent;
  let fixture: ComponentFixture<MonkeySearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonkeySearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MonkeySearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
