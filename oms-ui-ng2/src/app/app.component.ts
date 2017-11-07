import { Component, Renderer, OnInit, ChangeDetectorRef  } from '@angular/core';
import 'jquery';
import { Broadcaster } from "./common/services/broadcaster.service";
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from "./common/services/auth.service";
import { LocalStorageService } from "./common/services/local-storage.service";
import { ConstantConfig } from "./common/config/constant.config";
import { LoaderService } from './common/services/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  userName: string;
  isLoggedIN: boolean = false;
  showLoader: boolean;
  constructor(
    private renderer: Renderer,
    private broadcaster: Broadcaster,
    private router: Router,
    private authService: AuthService,
    private localStorageService: LocalStorageService,
    private loaderService: LoaderService,
    private changeDetectorRef: ChangeDetectorRef
  ) { }

  //handling route change events
  initRouteChangeEvents() {
    this.router.events.subscribe(event => {
      //verifying login
      if (event instanceof NavigationEnd) {
        this.isLoggedIN = this.authService.isLoggedIn();
        if (!this.isLoggedIN) {
          //this.router.navigate(['/']);
        } else {
          this.userName = this.localStorageService.get(ConstantConfig.AUTH_TOKEN);
        }
      }
      // NavigationEnd
      // NavigationCancel
      // NavigationError
      // RoutesRecognized
    });
  }
  ngOnInit() {
    this.registerBroadCastEvents();
    this.initRouteChangeEvents();

    this.loaderService.status.subscribe((val: boolean) => {
        this.showLoader = val;
    });

    //this.changeDetectorRef.detectChanges();
  }

  //loader show/hide
  //showLoader: string = 'N';
  registerBroadCastEvents() {
    //showLoader
    this.broadcaster.on<string>('SHOW_LOADER')
      .subscribe(message => {
        console.log('SHOW_LOADER', message);
        //this.showLoader = message;
      });
  }


}
