import { Routes } from '@angular/router';
import { Pantry } from './inventory/pantry/pantry';
import { Home } from './home/home';
import { Fridge } from './inventory/fridge/fridge';
import { Freezer } from './inventory/freezer/freezer';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'pantry', component: Pantry },
  { path: 'fridge', component: Fridge },
  { path: 'freezer', component: Freezer },
];
