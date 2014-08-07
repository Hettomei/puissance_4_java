package model;

public class Joueur {

  private int nbCoups;
  private int nbWin;
  private boolean vientDeGagner; // se remet à zero des quelle est consultée !
  private int nbLoose;
  private static int ID = 0;
  private int IDJoueur;

  public Joueur(){
    this.setVientDeGagner(false);
    this.setNbCoups(0);
    this.setNbWin(0);
    this.setNbLoose(0);
    this.IDJoueur = Joueur.ID;
    Joueur.ID++;
  }

  public int getNbCoups() {
    return nbCoups;
  }

  public int getID() {
    return IDJoueur;
  }

  private void setNbCoups(int nbCoups) {
    this.nbCoups = nbCoups;
  }

  public void setNbCoupsPlus1() {
    setNbCoups(nbCoups + 1);
  }

  public int getNbWin() {
    return nbWin;
  }

  private void setNbWin(int nbWin) {
    this.nbWin = nbWin;
  }

  public void setNbWinPlus1() {
    vientDeGagner = true;
    setNbWin(nbWin + 1);
  }

  public int getNbLoose() {
    return nbLoose;
  }

  private void setNbLoose(int nbLoose) {
    this.nbLoose = nbLoose;
  }

  public void setNbLoosePlus1() {
    setNbLoose(nbLoose + 1);
  }

  public boolean vientIlDeGagner() {
    if (vientDeGagner == true){
      vientDeGagner = false;
      return true;
    }
    return false;
  }

  private void setVientDeGagner(boolean vientDeGagner) {
    this.vientDeGagner = vientDeGagner;
  }

  public String toString(){
    return "" + getID();
  }
}
