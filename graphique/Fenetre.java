package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.GrilleCase;

public class Fenetre extends JFrame {

  private static final long serialVersionUID = 2026580921442617098L;
  private static final int COLONNE = 10;
  private static final int LIGNE = 10;
  private static final int NB_JOUEUR = 2;
  private static final int MARGIN_BUTTON = 4;
  public static final Color COULEUR_BG = Color.ORANGE;
  private GrilleCase gc ;
  private JPanel zonePrincipale = new JPanel();
  private JPanel zoneCase = new JPanel();

  public Fenetre() {
    this.setTitle("Puissance 4 Cube");
    this.setSize(900, 600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gc = new GrilleCase(COLONNE, LIGNE, NB_JOUEUR);

    setZonePrincipale();
    setZoneCase();

    this.setContentPane(zonePrincipale);

  }

  private void setZonePrincipale() {
    zonePrincipale.setBackground(Color.ORANGE);
    zonePrincipale.setLayout(new BorderLayout());

    JPanel zoneInfosN = new JPanel();
    zoneInfosN.setBackground(COULEUR_BG);
    zoneInfosN.add(new AffichageJoueur(gc));

    JPanel zoneInfosS = new JPanel();
    zoneInfosS.setBackground(COULEUR_BG);
    zoneInfosS.add(new AffichageStatsJoueurs(gc));

    JPanel zoneInfosE = new JPanel();
    zoneInfosE.setBackground(COULEUR_BG);
    zoneInfosE.add(new JLabel("Test"));

    JPanel zoneInfosW = new JPanel();
    zoneInfosW.setBackground(COULEUR_BG);
    zoneInfosW.add(new AffichageModifJeu(gc));


    zonePrincipale.add(zoneInfosN, BorderLayout.NORTH);
    zonePrincipale.add(zoneInfosS, BorderLayout.SOUTH);
    zonePrincipale.add(zoneInfosE, BorderLayout.EAST);
    zonePrincipale.add(zoneInfosW, BorderLayout.WEST);
    zoneCase.setBackground(COULEUR_BG);
    zonePrincipale.add(zoneCase, BorderLayout.CENTER);
  }

  private void setZoneCase(){

    GridLayout gl = new GridLayout(LIGNE, COLONNE);
    gl.setHgap(MARGIN_BUTTON);
    gl.setVgap(MARGIN_BUTTON);

    zoneCase.setLayout(gl);

    for(int i = 0; i < LIGNE ; i++){
      for(int j = 0; j < COLONNE ; j++){
        zoneCase.add(new AffichageCase(j,i, gc));
      }
    }
  }

}
