import {Component, OnInit} from '@angular/core';
import {ResourcesService} from '../../../../services/resources/resources.service';
import {MessageResponse} from '../../../../domains/resources/resources/messageResponse';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit{
  message!: string;
  constructor(private resourceService: ResourcesService) {
  }

  ngOnInit(): void {
    this.resourceService.getAdminMessage().subscribe({
      next: (res: MessageResponse) => this.message = res.message,
      error: (err: Error) => console.log(err),
      complete:() => console.log("done")
    });
  }
}
