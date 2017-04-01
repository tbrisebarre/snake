package snake;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;

public class Serpent {

      private LinkedList<Case> list;
      private Direction direction;
      private boolean estMort;
      private Direction demande;
      private int eatCount;
      private int moveCounter;
      
      public Serpent() {
            this.list = new LinkedList<Case>();
            this.list.add(new Case(14, 15));
            this.list.add(new Case(15, 15));
            this.list.add(new Case(16, 15));
            this.direction = Direction.GAUCHE;
      }
      
      public void setDemandeClavier(Direction demande) {
          this.demande = demande;
    }
      
      private void tourner() {
          if (this.demande != null) { // une touche à été pressée
                // le serpent va vers le haut ou le bas
                if (this.direction == Direction.HAUT
                            || this.direction == Direction.BAS) {
                      if (this.demande == Direction.DROITE) { // la touche droite
                                                                   // à été pressée
                            // le serpent tourne à droite
                            this.direction = Direction.DROITE;
                      } else if (this.demande == Direction.GAUCHE) { // la touche
                                                                          // gauche à
                                                                          // été pressée
                            // le serpent tourne à gauche
                            this.direction = Direction.GAUCHE;
                      }
                } else { // le serpent va vers la droite ou la gauche
                      if (this.demande == Direction.HAUT) { // la touche haut à
                                                                 // été pressée
                            // le serpent tourne vers le haut
                            this.direction = Direction.HAUT;
                      } else if (this.demande == Direction.BAS) { // la touche bas
                                                                       // à été pressée
                            // le serpent tourne vers le bas
                            this.direction = Direction.BAS;
                      }
                }
              
          }
    }
      
      private void avance() {
          // ajoute en tête de liste la case sur laquelle
          // le serpent doit se déplacer
          this.list.addFirst(getNextcase());
          // supprime le dernier élément de la liste
          this.list.removeLast();
    }
      
      private Case getNextcase() {
          Case tete = this.list.getFirst();
          switch (this.direction) {
                case HAUT:
                      return new Case(tete.getIndiceX(), tete.getIndiceY() - 1);
                case DROITE:
                      return new Case(tete.getIndiceX() + 1, tete.getIndiceY());
                case BAS:
                      return new Case(tete.getIndiceX(), tete.getIndiceY() + 1);
                case GAUCHE:
                      return new Case(tete.getIndiceX() - 1, tete.getIndiceY());
          }
          return null;
    }
      
      private boolean peutAvancer() {
    	  Case nextCase = getNextcase();
          return nextCase.estValide() && !this.list.contains(nextCase);
    }
      
      public boolean estMort() {
          return this.estMort;
    }
     
      private boolean peutManger(Grenouille grenouille) {
          return grenouille.equals(getNextcase());
    }
      
      private void mange() {
          
          this.list.addFirst(getNextcase());
       // comptabiliser les grenouilles mangé
          this.eatCount++;
    }
      
      public int getEatCount() {
          return this.eatCount;
    }
      
      private int getThresholdCounter(int niveau) {
          switch (niveau) {
                case 1:
                      return 20;
                case 2:
                      return 16;
                case 3:
                      return 14;
                case 4:
                      return 12;
                case 5:
                      return 10;
                case 6:
                      return 8;
                case 7:
                      return 6;
                case 8:
                      return 4;
                case 9:
                      return 3;
                default :
                      return 2;
          }
    }
      
      public void calcul(Grenouille grenouille, int niveau) {
    	// incrémente le compteur
          this.moveCounter++;
          // vérifie le temps d'animation du serpent
          if (this.moveCounter >= getThresholdCounter(niveau)) {
                // remettre le compteur à zéro 
                this.moveCounter = 0;
                // calcul du serpent
                tourner();
                if (peutManger(grenouille)) {
                      mange();
                      grenouille.nouvelleGrenouille();
                } else if (peutAvancer()) {
                      avance();
                } else {
                      // la partie est perdue car le serpent
                      // a atteint les limites du plateau de jeu
                      this.estMort = true;
                }
          }
      }
      
      
      public void affichage(Graphics g) {
          Graphics2D g2 = (Graphics2D) g;
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);
    	  for (Case box : this.list) {
              g.fillOval(box.getX(), box.getY(), box.getLargeur(), box.getHauteur());
        }
      }
      
}
