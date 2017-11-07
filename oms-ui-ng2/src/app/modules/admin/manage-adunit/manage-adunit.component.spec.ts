import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAdUnitComponent } from './manage-adunit.component';
import { LoginComponent } from '../../login/login.component';
import { inject } from '@angular/core/testing';

import { Injectable, ChangeDetectorRef } from '@angular/core';
import { AdminModule } from '../admin.module';
import { AppModule } from '../../../app.module';
import { Title, BrowserModule } from '@angular/platform-browser';
import { Http, BaseRequestOptions, HttpModule } from '@angular/http';
import { MockBackend } from '@angular/http/testing';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule, MdDialog } from '@angular/material';
import { Router, RouterModule } from '@angular/router';
import { HttpService } from '../../../common/services/http.service';
import { LocalStorageService } from "../../../common/services/local-storage.service";
import { Broadcaster } from '../../../common/services/broadcaster.service';
import { AuthService } from '../../../common/services/auth.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Credential } from '../../../common/models/credential';
import { ConstantConfig } from "../../../common/config/constant.config";
import { Observable } from 'rxjs/Observable';



@Injectable()
class MockTitle extends Title { }



describe('ManageAdUnitComponent', () => {
    let component: ManageAdUnitComponent;
    let fixture: ComponentFixture<ManageAdUnitComponent>;
    let httpService: HttpService;
    var auth_token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoidmlrYXMucGFyYXNoYXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDk3MzgxODM2LCJhdXRob3JpdGllcyI6WyJBZG1pbmlzdHJhdG9yIl0sImp0aSI6ImFlOGNiYjBiLTBjZGYtNDE2Yy1hZTM5LWZjYWQ1YTYyNjQzOCIsImNsaWVudF9pZCI6Im9tc2FwcCJ9.vTQlPezM-J0fISL2LlOpUH1e21zBTBOX8kzQMs665bw";

    beforeEach(() => {

        TestBed.configureTestingModule({
            // tslint:disable-next-line:max-line-length      
            imports: [AdminModule, AppModule],
            providers: [AuthService, LocalStorageService, Broadcaster, HttpService, MdDialog, FormBuilder, ChangeDetectorRef,
                MockBackend, BaseRequestOptions, Title,
                {
                    provide: Http, HttpService,
                    useFactory: (mockBackend, options) => {
                        return new Http(mockBackend, options);
                    },
                    deps: [MockBackend, BaseRequestOptions]
                },
                { provide: APP_BASE_HREF, useValue: '/' },
            ]
        })
            .compileComponents();

        fixture = TestBed.createComponent(ManageAdUnitComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });


    it('should showing all adUnit', inject([HttpService, Broadcaster, LocalStorageService, AuthService, MdDialog, FormBuilder, ChangeDetectorRef], (httpService, broadcaster, localStorage, authService, dialog, fb, cdr) => {
        localStorage.set(ConstantConfig.AUTH_TOKEN, auth_token);
        let component = new ManageAdUnitComponent(httpService, broadcaster, localStorage, authService, dialog, fb, cdr);
        component.adUnitListRefresh = true;
        expect(component).toBeTruthy();
    }));

    it('get adUnit by id', inject([HttpService, Broadcaster, LocalStorageService, AuthService, MdDialog, FormBuilder, ChangeDetectorRef], (httpService, broadcaster, localStorage, authService, dialog, fb, cdr) => {
        localStorage.set(ConstantConfig.AUTH_TOKEN, auth_token);
        let component = new ManageAdUnitComponent(httpService, broadcaster, localStorage, authService, dialog, fb, cdr);
        component.editAdUnit(189);
        expect(component).toBeTruthy();
    }));



    it('should adUnit update', inject([HttpService, Broadcaster, LocalStorageService, AuthService, MdDialog, FormBuilder, ChangeDetectorRef], (httpService, broadcaster, localStorage, authService, dialog, fb, cdr) => {
        localStorage.set(ConstantConfig.AUTH_TOKEN, auth_token);
        component = new ManageAdUnitComponent(httpService, broadcaster, localStorage, authService, dialog, fb, cdr);
        spyOn(httpService, 'put').and.returnValue(Observable.of(
            { "updated": 1497337783000, "name": "test abc", "displayName": "read", "capacity": null, "weight": null, "customer": null, "active": true })
        );
        let editJson = '{"updated":1497337783000,"adUnitId":189,"name":"testing","displayName":"read","capacity":null,"weight":null,"customer":null,"active":true}';
        component.newadUnit = JSON.parse(editJson);
        component.editMode = true;
        let formData = { valid: "true" };
        component.saveForm(formData);
        expect(component).toBeTruthy();
    }));


    it('should adUnit save', inject([HttpService, Broadcaster, LocalStorageService, AuthService, MdDialog, FormBuilder, ChangeDetectorRef], (httpService, broadcaster, localStorage, authService, dialog, fb, cdr) => {
        localStorage.set(ConstantConfig.AUTH_TOKEN, auth_token);
        spyOn(httpService, 'post').and.returnValue(Observable.of(
            { "updated": 1497337783000, "name": "test abc", "displayName": "read", "capacity": null, "weight": null, "customer": null, "active": true })
        );
        component = new ManageAdUnitComponent(httpService, broadcaster, localStorage, authService, dialog, fb, cdr);
        component.editMode = false;
        let editJson = '{"updated":1497337783000,"name":"test abc","displayName":"read","capacity":null,"weight":null,"customer":null,"active":true}';
        component.newadUnit = JSON.parse(editJson);
        let formData = { valid: "true" };
        component.saveForm(formData);
        expect(component).toBeTruthy();
    }));


});
