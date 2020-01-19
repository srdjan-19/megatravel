import { AddressService } from '../address.service';
import { AgentService } from '../agent.service';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Address } from 'src/app/model/address.model';
import { CreateAgentRequest } from '../agent.model';

@Component({
  selector: 'app-create-agent',
  templateUrl: './create-agent.component.html',
  styleUrls: ['./create-agent.component.css']
})
export class CreateAgentComponent implements OnInit {

  @Output() created = new EventEmitter();

  addresses: Address[];
  selectedIndex: number;
  agent = new CreateAgentRequest();

  constructor(private agentService: AgentService,
    private addressService: AddressService) { }

  ngOnInit() {
    this.addressService.fetchAddresses().subscribe(
      addresses => this.addresses = addresses
    )
  }

  setSelected(index: number) {
    this.selectedIndex = index + 1;
  }

  confirm() {
    this.agent.addressId = this.selectedIndex;
    this.agentService.create(this.agent).subscribe(
      response => {
        this.created.emit(response);
      }
    );
  }

}
