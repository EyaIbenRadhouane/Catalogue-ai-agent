import {
  Component,
  ElementRef,
  PLATFORM_ID,
  effect,
  inject,
  signal,
  viewChild,
} from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from './chat.service';
import { MarkdownPipe } from './markdown.pipe';

interface Message {
  auteur: 'user' | 'bot';
  texte: string;
  heure: string;
  erreur?: boolean;
}

@Component({
  selector: 'app-root',
  imports: [FormsModule, MarkdownPipe],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private chatService = inject(ChatService);
  private platformId = inject(PLATFORM_ID);
  private zone = viewChild<ElementRef<HTMLDivElement>>('zone');

  messages = signal<Message[]>([]);
  texte = signal('');
  attente = signal(false);

  suggestions = [
    'Avez-vous des produits électroniques ?',
    'Quel est le prix du Smartphone Samsung ?',
    'Le casque JBL est-il disponible ?',
  ];

  constructor() {
    effect(() => {
      this.messages();
      this.attente();

      if (!isPlatformBrowser(this.platformId)) return;

      setTimeout(() => {
        const el = this.zone()?.nativeElement;
        el?.scrollTo?.({ top: el.scrollHeight, behavior: 'smooth' });
      });
    });
  }

  private heure(): string {
    return new Date().toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
  }

  utiliser(suggestion: string) {
    this.texte.set(suggestion);
    this.envoyer();
  }

  effacer() {
    this.messages.set([]);
  }

  envoyer() {
    const message = this.texte().trim();
    if (!message || this.attente()) return;

    this.messages.update((m) => [...m, { auteur: 'user', texte: message, heure: this.heure() }]);
    this.texte.set('');
    this.attente.set(true);

    this.chatService.envoyer(message).subscribe({
      next: (rep) => {
        this.messages.update((m) => [
          ...m,
          {
            auteur: 'bot',
            texte: rep,
            heure: this.heure(),
            erreur: rep.includes('saturé'),
          },
        ]);
        this.attente.set(false);
      },
      error: () => {
        this.messages.update((m) => [
          ...m,
          {
            auteur: 'bot',
            texte: 'Connexion impossible avec le serveur.',
            heure: this.heure(),
            erreur: true,
          },
        ]);
        this.attente.set(false);
      },
    });
  }
}
