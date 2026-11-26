import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private http = inject(HttpClient);

  envoyer(message: string) {
    return this.http.get('/agent', { params: { message }, responseType: 'text' });
  }
}
