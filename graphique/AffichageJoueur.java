package graphique;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import controleur.GrilleCase;

public class AffichageJoueur extends JLabel implements Observer{

  private static final long serialVersionUID = 5832886641804505623L;

  public AffichageJoueur(GrilleCase gc) {
    super("Joueur N° " + gc.getJoueurEnCours());
    gc.addObserver(this);
  }

  @Override
  public void update(Observable t, Object o) {
    this.setText("Joueur N° " + ((GrilleCase) t).getJoueurEnCours());
  }

}
