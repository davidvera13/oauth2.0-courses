import {Component, OnInit} from '@angular/core';
import {ResourcesService} from '../../../../services/resources/resources.service';
import {MessageResponse} from '../../../../domains/resources/resources/messageResponse';

@Component({
  selector: 'app-user',
  imports: [],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {
  message!: string;

  constructor(private resourceService: ResourcesService) {
  }

  ngOnInit(): void {
    this.resourceService.getUserMessage().subscribe({
      next: (res: MessageResponse) => this.message = res.message,
      error: (err: Error) => console.log(err),
      complete: () => console.log("done")
    });
  }
}
