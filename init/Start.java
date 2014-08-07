package init;

import graphique.Fenetre;

public class Start {

  /**
   * @param args
   * @throws Exception
   */
  private static Fenetre f = null;

  public static void main(String[] args) throws Exception {
    f = new Fenetre();
    f.setVisible(true);
  }

  public static Fenetre getFenetre(){
    return f;
  }
}
