import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeSectionsComponent } from './home-sections.component';

describe('HomeSectionsComponent', () => {
  let component: HomeSectionsComponent;
  let fixture: ComponentFixture<HomeSectionsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeSectionsComponent]
    });
    fixture = TestBed.createComponent(HomeSectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
