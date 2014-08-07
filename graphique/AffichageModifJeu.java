package graphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controleur.GrilleCase;

public class AffichageModifJeu extends JPanel{


  /**
   *
   */
  private static final long serialVersionUID = -6917433611444578753L;
  private GrilleCase gc = null;

  public AffichageModifJeu(GrilleCase gc) {
    //super("Joueur N° " + gc.getJoueurEnCours());
    this.gc = gc;
    this.setBackground(Fenetre.COULEUR_BG);

    JButton nouvellePartie = new JButton("Nouvelle Partie");
    nouvellePartie.addActionListener(new BtnNouvellePartie());
    this.add(nouvellePartie);

    JButton moinsJoueur = new JButton("-");
    JButton plusJoueur = new JButton("+");
    moinsJoueur.addActionListener(new BtnMoinsJoueur());
    plusJoueur.addActionListener(new BtnPlusJoueur());
    this.add(moinsJoueur);
    this.add(plusJoueur);
  }

  class BtnNouvellePartie implements ActionListener{

    /**
     * Redéfinition de la méthode actionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      gc.nouvellePartie();
    }
  }

  class BtnMoinsJoueur implements ActionListener{

    /**
     * Redéfinition de la méthode actionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
  }


  class BtnPlusJoueur implements ActionListener{

    /**
     * Redéfinition de la méthode actionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      gc.ajouterUnJoueur();
    }
  }


}
