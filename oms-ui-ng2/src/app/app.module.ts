import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { DatepickerModule } from "ngx-bootstrap";

import { EmitterService } from './common/services/emitter.service';
import { Broadcaster } from './common/services/broadcaster.service';
import { HttpService } from './common/services/http.service';
import { LocalStorageService } from "./common/services/local-storage.service";

import { appRouting } from './common/routing/app.routing';
import { LoaderComponent } from './common/components/loader.component';
import { LoaderService } from './common/services/loader.service';


import { AppComponent } from './app.component';
import { LoginComponent } from './modules/login/login.component';
import { AuthService } from "./common/services/auth.service";
import { objectSharingService } from './common//services/objectSharingService';

import { AccordionModule } from 'ngx-bootstrap/accordion';
import { NotFoundComponent } from "./modules/not-found/not-found.component";
import { DashboardComponent } from "./modules/dashboard/dashboard.component";

import { TruncatePipe } from "./common/filter/truncate.pipe";

import { MultiselectDropdownModule } from 'angular-2-dropdown-multiselect';
import { MaterialModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { MalihuScrollbarModule } from 'ngx-malihu-scrollbar';
import { ClickOutSideModule } from "./common/directives/clickoutside.module";
import { DashboardProposalComponent } from "./modules/dashboard/proposal/dashboard-proposal.component";
import { ChartsModule } from "./common/modules/charts/charts.module";
import { DataTableModule } from "./common/modules/datatable/datatable.module";
import { CalendarService } from "./common/services/calendar.service";
import { DialogModule } from "./common/modules/dialog/dialog.module";

@NgModule({
  imports: [
    CarouselModule.forRoot(),
    AccordionModule.forRoot(),
    MalihuScrollbarModule.forRoot(),
    DatepickerModule.forRoot(),
    ClickOutSideModule.forRoot(),
    DialogModule.forRoot(),
    ChartsModule.forRoot(),
    DataTableModule.forRoot(),
    MultiselectDropdownModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    appRouting,
    MaterialModule,
    BrowserAnimationsModule,
    DialogModule.forRoot()

  ],
  declarations: [
    DashboardComponent,
    DashboardProposalComponent,
    LoaderComponent,
    AppComponent,
    LoginComponent,
    NotFoundComponent,
    TruncatePipe
  ],
  providers: [
    HttpService,
    EmitterService,
    LocalStorageService,
    AuthService,
    objectSharingService,
    Broadcaster,
    CalendarService,
    LoaderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
