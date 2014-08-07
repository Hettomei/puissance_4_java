package model;

public class Case {

  private Joueur appartientA;

  public Case(){
    appartientA = null;
  }

  public Joueur getAppartientA() {
    return appartientA;
  }

  public void setAppartientA(Joueur j) {
    this.appartientA = j;
  }

  public String toString(){
    return this.appartientA + "";
  }

}
