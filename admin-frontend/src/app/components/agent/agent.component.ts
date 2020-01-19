import { Component, OnInit } from '@angular/core';
import { Agent, CreateAgentRequest } from './agent.model';
import { AgentService } from './agent.service';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.css']
})
export class AgentComponent implements OnInit {

  agents: Agent[] = [];

  isAdmin: boolean;

  constructor(private agentService: AgentService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.agentService.fetchAgents().subscribe(
      response => { this.agents = response }
    )

    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN'))
      this.isAdmin = true;
  }

  applyCreation(agent: Agent) {
    this.agents.push(agent);
  }
}
