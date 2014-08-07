package graphique;

import init.Tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import controleur.GrilleCase;

public class AffichageCase extends JButton implements MouseListener, Observer{

  private static final long serialVersionUID = -8301360324279214401L;
  private static final Color COULEUR_INACTIF =  Color.WHITE;
  //Avec ces couleurs : seul 7 joueurs max;
  private Color couleur[] = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PINK, Color.BLACK, Color.CYAN} ;
  private GrilleCase gc ;
  private int column;
  private int row;

  public AffichageCase(int column, int ligne, GrilleCase gc){
    //	super( column + "-" + ligne + " " + ++iii );
    this.setFont(new Font("Dialog", Font.PLAIN, 9 ));
    this.setBorderPainted(false);
    this.setOpaque(true);
    this.addMouseListener(this);
    this.gc = gc;
    this.column = column;
    this.row = ligne;
    this.setBackground(COULEUR_INACTIF); // Couleur vide
    gc.addObserver(this);
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    try {
      gc.setActif(column);
    } catch (Exception e) {
      Tools.P(e.getMessage());
    }
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
    setBgColor((gc.getJoueurEnCours().getID()));
  }

  @Override
  public void mousePressed(MouseEvent arg0) {	}

  @Override
  public void mouseReleased(MouseEvent arg0) { }

  @Override
  public void mouseExited(MouseEvent arg0) {
    getGoodColorForThisCase(gc);
  }

  @Override
  public void update(Observable t, Object o) {
    getGoodColorForThisCase((GrilleCase)t);
  }

  private void getGoodColorForThisCase(GrilleCase grille) {
    if(grille.getEtat(column, row) != null){
      setBgColor(grille.getEtat(column, row).getID());
    }else{
      this.setBackground(COULEUR_INACTIF);
    }
  }

  private void setBgColor(int i) {
    Color temp;
    //7 nombre de couleur definis par defaut, apres on calcul
    if (i < 7){
      temp = couleur[i];
    }else{
      temp = customColorByIdJoueur(i);
    }
    this.setBackground(temp);
  }

  /*
   * Fonction permettant de créé une couleur plutot clair en fonction de l'id. A tester
   */
  private Color customColorByIdJoueur(int i){
    int i1 = (i + 150) % 255 ;
    int i2 = i1 ;
    int i3 = i2 ;
    int variable = (i*50) % 255 ;

    if(i % 3 == 0){
      i1 = variable ;
    }else if(i % 2 == 0){
      i2 = variable ;
    }else if(i % 1 == 0){
      i3 = variable ;
    }
    return new Color(i1, i2, i3);
  }
}
