import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from "../../common/services/local-storage.service";
import { AuthService } from "../../common/services/auth.service";
import { Credential } from '../../common/models/credential';
import { ConstantConfig } from "../../common/config/constant.config";
import { SignUpData } from '../../common/models/SignUpData';
import { PathConfig } from '../../common/config/path.config';
import { HttpService } from '../../common/services/http.service';
import { createPassword } from '../../common/models/SignUpData';
import { ActivatedRoute } from '@angular/router';

@Component({

  styleUrls: ['./login.component.scss'],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  pwdAttributeType: string = 'password';
  pwdVal: string = 'Show';
  credential: Credential = new Credential();
  errorMessage: string = '';
  copyright = ConstantConfig.COPY_RIGHT;
  invalidLogin: Boolean = false;
  isSignUp: Boolean = false;
  isSignInVerified: Boolean = false;
  signUpData: SignUpData = new SignUpData();
  createPassword: createPassword = new createPassword();
  emailMessage: string = undefined;
  verificationMail: boolean = false;
  paramToken: string;
  confirmErrorMsg: string = undefined;
  constructor(private router: Router, private route: ActivatedRoute, private httpService: HttpService, private localStorage: LocalStorageService, private authService: AuthService) { }

  ngOnInit() {
    //this.router.navigate(['dashboard']);
    this.route.params.subscribe(params => {
      if (params['token'] != undefined) {
        this.paramToken = params['token'];
      }
      // In a real app: dispatch action to load the details here.
    });
  }
  /**
    * Login a user
    */

  submitSignUp() {
    var auth_token = this.localStorage.get(ConstantConfig.AUTH_TOKEN);
    let url = PathConfig.BASE_URL_API + PathConfig.SIGNUP_API;
    //var data = this.httpService.postWithoutAuth("http://10.193.74.137:7070/oms-media/registration",this.signUpData);
    var data = this.httpService.postWithoutAuth(url, this.signUpData);
    let self = this;
    data.subscribe(
      data => {
        if (data) {
          if (data.status == 'PARTIAL_SUCCESS') {
            this.emailMessage = data.message;
            let token = data.message.split('/')[data.message.split('/').length - 1];
            let url = PathConfig.BASE_URL_API + PathConfig.SIGNUP_CONFIRM_API + "?token=" + token;
            //var verficationData = this.httpService.getWithAuth("http://10.193.74.137:7070/oms-media/registration/confirm?token="+token);
            var verficationData = this.httpService.getWithAuth(url);
            verficationData.subscribe(
              verficationData => {
                if (verficationData) {
                  console.log(verficationData);
                  this.createPassword.username = verficationData.user.username;
                  this.isSignInVerified = true;
                } else {

                }
                return verficationData;

              });
            this.verificationMail = true;
          }
        }
      });
    return data;
  }

  cancelSignUp(){
    this.isSignUp = false;
  }

  toggleInvalid() {
    (this.invalidLogin == true) ? this.invalidLogin = !this.invalidLogin : void (0);
  }
  saveNewPassword() {
    if (this.createPassword.password.localeCompare(this.createPassword.confirmPassword) == 0) {
      let url = PathConfig.BASE_URL_API + PathConfig.SET_REGISTRATION;
      //var data = this.httpService.putWithourAuth("http://10.193.74.137:7070/oms-media/registration/setPassword",{'username':this.createPassword.username,'password':this.createPassword.password});
      var data = this.httpService.putWithourAuth(url, { 'username': this.createPassword.username, 'password': this.createPassword.password });

      data.subscribe(
        data => {
          console.log(data);
          this.router.navigate(['login']);

        });
    } else {
      this.createPassword.password = "";
      this.createPassword.confirmPassword = "";
      this.confirmErrorMsg = "";
    }

  }

  login() {
    this.errorMessage = '';
    this.authService.login(this.credential)
      .subscribe(
      data => {
        if (data.access_token) {
          this.localStorage.set(ConstantConfig.AUTH_TOKEN, data.access_token);
          this.router.navigate(['dashboard/proposal']);
        }else{
          this.router.navigate(['login']);
        }

      },
      err => {
        this.errorMessage = err;
        this.invalidLogin = true;
      }
      );
  }

  keyDownFunction(event) {
    if (event.keyCode == 13) {
      this.login();
    }
  }



}
