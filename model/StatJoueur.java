package model;

public class StatJoueur {

  private int nbCoups;
  private int nbWin;
  private int nbLoose;

  public StatJoueur(){
    this.setNbCoups(0);
    this.setNbWin(0);
    this.setNbLoose(0);
  }

  public int getNbCoups() {
    return nbCoups;
  }

  public void setNbCoups(int nbCoups) {
    this.nbCoups = nbCoups;
  }

  public void setNbCoupsPlus1() {
    setNbCoups(nbCoups + 1);
  }

  public int getNbWin() {
    return nbWin;
  }

  public void setNbWin(int nbWin) {
    this.nbWin = nbWin;
  }

  public void setNbWinPlus1() {
    setNbWin(nbWin + 1);
  }

  public int getNbLoose() {
    return nbLoose;
  }

  public void setNbLoose(int nbLoose) {
    this.nbLoose = nbLoose;
  }

  public void setNbLoosePlus1() {
    setNbLoose(nbLoose + 1);
  }
}
