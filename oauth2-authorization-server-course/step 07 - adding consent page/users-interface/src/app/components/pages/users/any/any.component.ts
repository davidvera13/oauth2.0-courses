import {Component, OnInit} from '@angular/core';
import {ResourcesService} from '../../../../services/resources/resources.service';
import {MessageResponse} from '../../../../domains/resources/resources/messageResponse';

@Component({
  selector: 'app-any',
  imports: [],
  templateUrl: './any.component.html',
  styleUrl: './any.component.css'
})
export class AnyComponent implements OnInit {
  message!: string;

  constructor(private resourceService: ResourcesService) {
  }

  ngOnInit(): void {
    this.resourceService.getAnyMessage().subscribe({
      next: (res: MessageResponse) => this.message = res.message,
      error: (err: Error) => console.log(err),
      complete: () => console.log("done")
    });
  }
}
