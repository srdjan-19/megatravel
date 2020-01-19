import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AccommodationService } from '../../services/accommodation.service';
import { ReservationService } from '../../services/reservation.service';
import { Accommodation, SearchAccommodationRequest } from '../../model/accommodation.model';
import { CreateReservationRequest } from '../../model/reservation.model';
import { TokenStorageService } from '../../auth/token-storage.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-accommodation',
  templateUrl: './accommodation.component.html',
  styleUrls: ['./accommodation.component.css'],
  providers: [DatePipe]
})
export class AccommodationComponent implements OnInit {

  accommodation: Accommodation;
  accommodations: Accommodation[];
  filteredAccommodations: Accommodation[];
  reservation = new CreateReservationRequest;

  private criteria: SearchAccommodationRequest;

  isEndUser: boolean = false;
  page: number = 0;

  private searchName = "";
  private searchType = "";
  private searchCategory = "";
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
    private datePipe: DatePipe,
    private notification: ToastrService) {
  }

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
    }

    this.accommodationService.fetchAccommodations(this.page).subscribe(
      data => {
        this.accommodations = data;

        this.notification.info("Loaded page number 1.")
      }
    )
  }

  reserve(index: string): void {
    this.accommodation = this.accommodations[index];
    this.reservation.accommodationName = this.accommodation.name;

    if (this.selectedFrom != null)
      this.reservation.fromDate = this.selectedFrom;
    if (this.selectedTill != null)
      this.reservation.tillDate = this.selectedTill;

    this.reservationService.create(this.reservation).subscribe(
      data => {
        this.notification.success(`You have been successfully made an reservation in ${this.accommodation.name}!`, "Success", { timeOut: 5000 })
      },
      error => {
        this.notification.error(error.error.message, "Error", { timeOut: 2000 })
      }

    )
  }

  search() {
    this.criteria = new SearchAccommodationRequest(this.searchName, this.searchType, this.searchCategory)
    this.accommodationService.search(this.criteria).subscribe(
      response => this.accommodations = response
    )
  }

  previousPage() {
    if (this.page > 0)
      this.page--;

    this.accommodationService.fetchAccommodations(this.page).subscribe(
      data => {
        this.accommodations = data;
      }
    )

  }

  nextPage() {
    if (this.page < 19)
      this.page++;

    this.accommodationService.fetchAccommodations(this.page).subscribe(
      data => {
        this.accommodations = data;
        this.notification.info(`Page number ${this.page + 1} has been loaded.`)
      }
    )

  }

  changePage(page: number) {
    this.page = page;
    this.accommodationService.fetchAccommodations(page).subscribe(
      data => {
        this.accommodations = data;
        this.notification.info(`Page number ${this.page + 1} has been loaded.`)
      }
    )
  }

}
