import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../model/accommodation.model';
import { Image } from '../model/image.model';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http : HttpClient){
        
  }
  uploadImages(fd : FormData){
    return this.http.post('api/images/upload',fd).subscribe();
 }

 getImagesIdsByAcc(acc : Accommodation){
   return this.http.post<number[]>('api/images/getImageIdsByAcc', acc);
 }
  getImage(id : number){

    return this.http.post('api/images/getImage',id, {responseType: 'blob'});

  }

}
