package graphique;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.GrilleCase;

public class AffichageStatsJoueurs extends JPanel implements MouseListener, Observer{

  private static final long serialVersionUID = 5832886641804505623L;

  private JPanel [] jp;

  public AffichageStatsJoueurs(GrilleCase gc) {
    this.setBackground(Fenetre.COULEUR_BG);
    gc.addObserver(this);
    jp = new JPanel[gc.getNbJoueur()+1];

    for(int i = 1 ; i <= gc.getNbJoueur() ; i++){
      jp[i] = new JPanel();

      GridLayout gl = new GridLayout(4, 1);


      jp[i].setLayout(gl);

      jp[i].setBorder(BorderFactory.createTitledBorder("Joueur " + i));
      jp[i].add(new JLabel("                        "));
      this.add(jp[i]);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void update(Observable t, Object o) {
    GrilleCase gc = (GrilleCase) t;
    for(int i = 1 ; i <= gc.getNbJoueur() ; i++){
      jp[i].removeAll();
      jp[i].add(new JLabel("Coups joué : " + gc.getStatJoueur(i).getNbCoups()));
      jp[i].add(new JLabel("Gagné : " + gc.getStatJoueur(i).getNbWin()));
      jp[i].add(new JLabel("Perdu : " + gc.getStatJoueur(i).getNbLoose()));
    }
  }

}
