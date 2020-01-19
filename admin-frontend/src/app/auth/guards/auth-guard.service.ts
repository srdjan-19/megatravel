import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private tokenStorage: TokenStorageService,
              private router: Router) { }


  canActivate(route: import("@angular/router").ActivatedRouteSnapshot, state: import("@angular/router").RouterStateSnapshot): boolean | import("@angular/router").UrlTree | import("rxjs").Observable<boolean | import("@angular/router").UrlTree> | Promise<boolean | import("@angular/router").UrlTree> {
    
    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN')) {
      return true;
    } else { 
      this.router.navigate(['homepage']);
      return false;
    }
  }

}
