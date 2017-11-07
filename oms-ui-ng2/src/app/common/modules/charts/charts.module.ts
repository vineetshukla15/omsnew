import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { AmChartsModule } from "@amcharts/amcharts3-angular";
import { AmChartsService } from "@amcharts/amcharts3-angular";

import {ChartComponent} from "./chart.component";


@NgModule({
  imports: [BrowserModule,CommonModule,AmChartsModule],
  declarations: [
    ChartComponent
  ],
  providers: [
    AmChartsService
    ],
  exports: [ChartComponent]
})
export class ChartsModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: ChartsModule
    }
  }
}
