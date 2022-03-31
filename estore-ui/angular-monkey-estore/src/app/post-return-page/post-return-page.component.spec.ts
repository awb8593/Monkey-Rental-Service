import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostReturnPageComponent } from './post-return-page.component';

describe('PostReturnPageComponent', () => {
  let component: PostReturnPageComponent;
  let fixture: ComponentFixture<PostReturnPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostReturnPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostReturnPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
