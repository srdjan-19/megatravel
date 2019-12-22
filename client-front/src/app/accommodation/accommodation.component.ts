import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AccommodationService } from '../services/accommodation.service';
import { ReservationService } from '../services/reservation.service';
import { Accommodation, SearchAccommodationRequest } from '../model/accommodation.model';
import { CreateReservationRequest } from '../model/reservation.model';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-accommodation',
  templateUrl: './accommodation.component.html',
  styleUrls: ['./accommodation.component.css'],
  styles: [` 
  .rating {
    font-size: 48px;
    color: orange;
    display: inline-block;
    overflow: hidden;
  }
  .rating::before { 
    content: "★★★★★" 
  }
  `],
  providers: [DatePipe]
})
export class AccommodationComponent implements OnInit {

  accommodation: Accommodation;
  accommodations: Accommodation[];
  filteredAccommodations: Accommodation[];
  reservation = new CreateReservationRequest;

  criteria: SearchAccommodationRequest;

  isEndUser: boolean = false;
  private roles: string[];

  private searchName: string;
  private searchType: string;
  private searchCategory: string;
  private searchFromDate: Date;
  private searchTillDate: Date;

  private today: Date;
  private minDate: string;
  private minDateTill: string;

  public _selectedFrom: Date;
  public _selectedTill: Date;


  constructor(private accommodationService: AccommodationService,
    private reservationService: ReservationService,
    private tokenStorage: TokenStorageService,
    private datePipe: DatePipe) { }

  set selectedFrom(date: Date) {
    this.reservation.fromDate = date;
  }

  set selectedTill(date: Date) {
    this.reservation.tillDate = date;
  }

  sortByCategory(): void {
    this.accommodations.sort((a, b) => a.category.name.localeCompare(b.category.name));
  }

  sortByType(): void {
    this.accommodations.sort((a, b) => a.type.name.localeCompare(b.type.name));
  }

  sortByDistance(): void {
    this.accommodations.sort((a, b) => a.distance - b.distance);
  }

  sortByRating(): void {
    this.accommodations.sort((a, b) => a.rate - b.rate);
  }

  ngOnInit() {
    this.today = new Date();
    this.minDate = this.datePipe.transform(this.today, 'yyyy-MM-dd');
    this.minDateTill = this.datePipe.transform(this.today, 'yyyy-MM-dd');

    if (this.tokenStorage.getToken() != null) {
      if (this.tokenStorage.getAuthorities().includes('ROLE_END_USER'))
        this.isEndUser = true;
      this.roles = this.tokenStorage.getAuthorities();
    }


    this.accommodationService.getAccommodations().subscribe(
      data => {
        this.accommodations = data;
      }
    )
  }

  reserve(index: string): void {
    this.accommodation = this.accommodations[index];
    this.reservation.accommodationName = this.accommodation.name;
    console.log(this.reservation);


    if (this.selectedFrom != null)
      this.reservation.fromDate = this.selectedFrom;
    if (this.selectedTill != null)
      this.reservation.tillDate = this.selectedTill;

    this.reservationService.create(this.reservation).subscribe(
      data => {
        alert(data.feedback)
      },
      err => {
        alert(err.error.message)
      }

    )
  }

  search() {
    this.criteria = new SearchAccommodationRequest();
    this.criteria.category = this.searchCategory;
    this.criteria.name = this.searchName;
    this.criteria.type = this.searchType;
    this.accommodationService.search(this.criteria).subscribe(
      response => this.accommodations = response
    )
  }

}
