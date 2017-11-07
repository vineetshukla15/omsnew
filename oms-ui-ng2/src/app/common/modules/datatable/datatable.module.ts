import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DTComponent } from './datatable.component';
import { DTComponentClientSide } from "./datatable.clientside.component";


@NgModule({
  imports: [CommonModule],
  declarations: [
    DTComponent,
    DTComponentClientSide
  ],
  exports: [DTComponent, DTComponentClientSide]
})
export class DataTableModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: DataTableModule
    }
  }
}
