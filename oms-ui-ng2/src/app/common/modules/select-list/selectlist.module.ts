import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectListComponent } from "./selectlist.component";
import { MaterialModule } from "@angular/material";
import { FormsModule } from "@angular/forms";
import { MalihuScrollbarModule } from 'ngx-malihu-scrollbar';

@NgModule({
  imports: [CommonModule, MaterialModule, FormsModule, MalihuScrollbarModule.forRoot()],
  declarations: [
    SelectListComponent
  ],
  exports: [SelectListComponent]
})
export class SelectListModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SelectListModule
    }
  }
}
