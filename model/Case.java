package model;

public class Case {

  public static final int INACTIF = 0;
  private int appartientA;

  public Case(){
    appartientA = INACTIF;
  }

  public int getAppartientA() {
    return appartientA;
  }

  public void setAppartientA(int i) {
    this.appartientA = i;
  }

  public String toString(){
    return this.appartientA + "";
  }

}
