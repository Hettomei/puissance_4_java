package graphique;

import init.Start;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controleur.GrilleCase;

public class AffichageStatsJoueurs extends JPanel implements Observer{

  private static final long serialVersionUID = 5832886641804505623L;

  private JPanel [] jp;

  public AffichageStatsJoueurs(GrilleCase gc) {
    this.setBackground(Fenetre.COULEUR_BG);
    gc.addObserver(this);
    jp = new JPanel[gc.getNbJoueur()];

    for(int i = 0 ; i < gc.getNbJoueur() ; i++){
      jp[i] = new JPanel();

      GridLayout gl = new GridLayout(4, 1);


      jp[i].setLayout(gl);

      jp[i].setBorder(BorderFactory.createTitledBorder("Joueur " + i));
      jp[i].add(new JLabel("                        "));
      this.add(jp[i]);
    }
  }

  @Override
  public void update(Observable t, Object o) {
    GrilleCase gc = (GrilleCase) t;
    for(int i = 0 ; i < gc.getNbJoueur() ; i++){
      jp[i].removeAll();
      jp[i].add(new JLabel("Coups joué : " + gc.getStatJoueur(i).getNbCoups()));
      jp[i].add(new JLabel("Gagné : " + gc.getStatJoueur(i).getNbWin()));
      jp[i].add(new JLabel("Perdu : " + gc.getStatJoueur(i).getNbLoose()));

      if (gc.getStatJoueur(i).vientIlDeGagner() == true){
        JOptionPane.showMessageDialog(Start.getFenetre(), "Le joueur " + gc.getWinner() + " gagne");
      }
    }
  }

}
