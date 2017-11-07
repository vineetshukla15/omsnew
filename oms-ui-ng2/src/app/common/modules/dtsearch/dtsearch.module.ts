import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";

import { DTSearchComponent } from "./dtsearch.component";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import {ClickOutSideModule} from "../../../common/directives/clickoutside.module";

@NgModule({
  
  imports: [FormsModule ,CommonModule, BsDropdownModule.forRoot(), ClickOutSideModule.forRoot()],
  
  declarations: [
    DTSearchComponent
  ],
  exports: [DTSearchComponent]
})
export class DataTableSearchModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: DataTableSearchModule
    }
  }
}
