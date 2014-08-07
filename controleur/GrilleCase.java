package controleur;

import init.Tools;

import java.util.Observable;

import model.Case;
import model.StatJoueur;

public class GrilleCase extends Observable{
  private static final int DEFAULT_COLUMN = 10;
  private static final int DEFAULT_ROWS = 10;

  private Case lesCase[][];

  private int nbColumns;
  private int nbRows;
  private int nbJoueurs;
  private int nbCasePourGagner;

  private int joueurEnCours;

  private int winner;


  private StatJoueur [] joueurs ;


  public GrilleCase(){
    this.nbColumns = DEFAULT_COLUMN;
    this.nbRows = DEFAULT_ROWS;
    this.lesCase = new Case[nbColumns][nbRows];
    this.joueurEnCours = 1;
    this.nbJoueurs = 2;
    this.winner = 0;
    this.nbCasePourGagner = 4;
    initJoueurs();
    initGrid();
  }


  public GrilleCase(int columns, int rows, int nbJoueurs){
    this.nbColumns = (columns > 0 ? columns : DEFAULT_COLUMN);
    this.nbRows = (rows > 0 ? rows : DEFAULT_ROWS);
    this.lesCase = new Case[nbColumns][nbRows];
    this.joueurEnCours = 1;
    this.nbJoueurs = (nbJoueurs > 0 ? nbJoueurs : 1);
    this.winner = 0;
    this.nbCasePourGagner = 3;
    initJoueurs();
    initGrid();
  }

  public int getNbJoueur() {
    return nbJoueurs;
  }

  public int getWinner() {
    return winner;
  }

  private void initJoueurs() {
    joueurs = new StatJoueur[nbJoueurs+1]; //On demarrera a l'indice 1
    for (int i = 1 ; i <= nbJoueurs; i++){
      joueurs[i] = new StatJoueur();
    }
  }

  public StatJoueur getStatJoueur(int numeroJoueur){
    if (numeroJoueur < 1 || numeroJoueur > nbJoueurs)
      return null;
    return joueurs[numeroJoueur];
  }

  public int getJoueurEnCours(){
    return joueurEnCours;
  }

  private void initGrid(){
    for (int row = 0; row < this.nbRows; row++){
      for (int col = 0; col < this.nbColumns; col++){
        this.lesCase[col][row] = new Case();
      }
    }
    Tools.P("Grille créé : " + nbColumns + " colonnes et " + nbRows + " lignes");
  }

  public void showGrille(){
    for (int row = 0; row < this.nbRows; row++){
      for (int col = 0; col < this.nbColumns; col++){
        System.out.print( this.lesCase[col][row] + "  ");
      }
      System.out.println();
    }
  }

  /*
   * Active la case la plus basse de la colonne
   * Ici on fait tous les tests et mise à jour
   */
  public void setActif(int colonne) throws Exception {
    checkColumnError(colonne);

    boolean flag = true;
    int ligne = nbRows - 1;

    //on parcours toutes les cases. De bas en haut
    //on active la case la plus basse non activée tel un puissance 4 physique
    while(flag && ligne >= 0){
      if(lesCase[colonne][ligne].getAppartientA() == Case.INACTIF){
        flag = false;
        lesCase[colonne][ligne].setAppartientA(joueurEnCours);
        Tools.P("La case " + "c"+ colonne + "-l" + ligne + " appartient a Joueur " + lesCase[colonne][ligne]);
        ajouterCoupJoueurEnCour();
        checkIfWinner(colonne, ligne);
        avancerJoueurEnCour();
        setChanged();
        notifyObservers();
      }
      ligne--;
    }
  }

  /*
   * On se base sur la derniere case jouée du dernier joueur
   * on tourne autour, si 4 case -> "winner" = id du joueur;
   */
  //TODO : améliorer ce truc
  private void checkIfWinner(int colonne, int ligne) {
    int joueurCaseInitiale = lesCase[colonne][ligne].getAppartientA();
    int nbCaseMemeJoueur = 0;

    //On regarde toute la ligne donc on deplace la colonne
    nbCaseMemeJoueur  = checkLigneColonneDiag(colonne - 1, ligne, -1, 0, joueurCaseInitiale);
    nbCaseMemeJoueur += checkLigneColonneDiag(colonne + 1, ligne,  1, 0, joueurCaseInitiale);
    nbCaseMemeJoueur++; //add the first case;

    //Si pas de gagnant, on test la suite
    if (!updateIfWinner(joueurCaseInitiale, nbCaseMemeJoueur)){
      //On regarde toute la colonne donc on deplace la ligne
      nbCaseMemeJoueur  = checkLigneColonneDiag(colonne, ligne - 1, 0, -1, joueurCaseInitiale);
      nbCaseMemeJoueur += checkLigneColonneDiag(colonne, ligne + 1, 0,  1, joueurCaseInitiale);
      nbCaseMemeJoueur++; //add the first case;
      if (!updateIfWinner(joueurCaseInitiale, nbCaseMemeJoueur)){
        //Test de diagonal de hau gauche vers bas droite
        nbCaseMemeJoueur  = checkLigneColonneDiag(colonne - 1, ligne - 1, -1, -1, joueurCaseInitiale);
        nbCaseMemeJoueur += checkLigneColonneDiag(colonne + 1, ligne + 1,  1,  1, joueurCaseInitiale);
        nbCaseMemeJoueur++; //add the first case;
        if (!updateIfWinner(joueurCaseInitiale, nbCaseMemeJoueur)){
          //Test de diagonal de bas gauche vers haut droit
          nbCaseMemeJoueur  = checkLigneColonneDiag(colonne - 1, ligne + 1, -1,  1, joueurCaseInitiale);
          nbCaseMemeJoueur += checkLigneColonneDiag(colonne + 1, ligne - 1,  1, -1, joueurCaseInitiale);
          nbCaseMemeJoueur++; //add the first case;
          updateIfWinner(joueurCaseInitiale, nbCaseMemeJoueur);

        }else{
          return;
        }
      }else{
        return;
      }
    }
    else{
      return;
    }

  }

  /*
   * Si il y a un gagnant : on met a jour les stats et on affiche le message et on renvoie TRUE
   * Sinon on renvoit false
   */
  private boolean updateIfWinner(int joueurCaseInitiale, int nbCaseMemeJoueur) {
    if (nbCaseMemeJoueur >= nbCasePourGagner){
      miseAJourGagnantPerdant(joueurCaseInitiale);
      winner = joueurCaseInitiale ;
      Tools.P("Le joueur " + winner + " gagne avec " + nbCaseMemeJoueur + " cases");
      return true;
    }
    return false;
  }

  private int checkLigneColonneDiag(int colonne, int ligne, int stepCol, int stepRow, int joueurAChecker){

    boolean flag = true;
    int nbCaseMemeJoueur = 0;

    while (flag && colonne >= 0 && colonne < nbColumns && ligne >= 0 && ligne < nbRows){
      if (lesCase[colonne][ligne].getAppartientA() == joueurAChecker)
        nbCaseMemeJoueur++;
      else
        flag = false;

      colonne += stepCol ;
      ligne += stepRow ;
    }

    return nbCaseMemeJoueur;
  }
  private void miseAJourGagnantPerdant(int joueurGagnant) {
    joueurs[joueurGagnant].setNbWinPlus1();
    for (int i = 1 ; i <= nbJoueurs; i++){
      if(i != joueurGagnant)
        joueurs[i].setNbLoosePlus1();
    }
  }


  private void ajouterCoupJoueurEnCour() {
    joueurs[joueurEnCours].setNbCoupsPlus1();
  }

  /*
   * Passe la main au suivant. retourne au joueur numéro 1 apres le dernier joueur
   */
  private void avancerJoueurEnCour() {
    joueurEnCours++;
    if (joueurEnCours > nbJoueurs){
      joueurEnCours = 1;
    }
  }

  private void checkColumnError(int colonne) throws Exception {
    if (colonne < 0 || colonne >= this.nbColumns){
      throw new Exception("Cette colonne n'existe pas : " + colonne);
    }
  }

  private boolean checkValidColRow(int column, int row) throws Exception{
    if (column < 0 || column >= this.nbColumns || row < 0 || row >= this.nbRows){
      throw new Exception("Aucune case existante pour c" + column + " l" + row);
    }
    return true;
  }

  /*
   * Renvoie 0 si inactif. Sinon renvoie l'ID du joueur possédant la case
   */
  public int getEtat(int col, int row) throws Exception {
    checkValidColRow(col, row);
    return lesCase[col][row].getAppartientA();
  }

  public void nouvellePartie() {
    this.lesCase = new Case[nbColumns][nbRows];
    this.joueurEnCours = 1;
    initGrid();
    setChanged();
    notifyObservers();
  }
}
