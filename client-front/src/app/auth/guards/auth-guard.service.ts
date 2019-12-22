import { Injectable } from '@angular/core';
import { TokenStorageService } from '../token-storage.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private tokenStorage: TokenStorageService,
              private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
    if (this.tokenStorage.getAuthorities().includes('ROLE_END_USER')) {
      return true;
    } else { 
      this.router.navigate(['pnf-404']);
      return false;
    }
  }
  
}
