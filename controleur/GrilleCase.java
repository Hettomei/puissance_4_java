package controleur;

import init.Tools;

import java.util.ArrayList;
import java.util.Observable;

import model.Case;
import model.Joueur;

public class GrilleCase extends Observable{

  private static final int DEFAULT_COLUMN 			= 10;
  private static final int DEFAULT_ROWS 				= 10;
  private static final int NB_CASE_POUR_GAGNER_DEFAUT = 4;

  private Case lesCase[][];

  private int nbColumns;
  private int nbRows;
  private int nbJoueurs;
  private int nbCasePourGagner;

  private int joueurEnCours;

  private Joueur winner;


  private ArrayList<Joueur> joueurs ;


  public GrilleCase(){
    initColumnsRowGrille(DEFAULT_COLUMN, DEFAULT_ROWS);
    this.nbCasePourGagner = NB_CASE_POUR_GAGNER_DEFAUT;
    initJoueurs(2);
    initGrid();
  }


  public GrilleCase(int columns, int rows, int nbJoueurs){
    initColumnsRowGrille(columns, rows);
    this.nbCasePourGagner = NB_CASE_POUR_GAGNER_DEFAUT;
    initJoueurs(nbJoueurs);
    initGrid();
  }


  private void initColumnsRowGrille(int columns, int rows) {
    this.nbColumns = (columns > 0 ? columns : DEFAULT_COLUMN);
    this.nbRows = (rows > 0 ? rows : DEFAULT_ROWS);
    this.lesCase = new Case[nbColumns][nbRows];
  }

  public int getNbJoueur() {
    return nbJoueurs;
  }

  public Joueur getWinner() {
    return winner;
  }

  private void initJoueurs(int nbJoueurs) {
    this.winner = null;
    this.joueurEnCours = 0;
    this.nbJoueurs = (nbJoueurs > 0 ? nbJoueurs : 1);
    this.joueurs = new ArrayList<Joueur>();
    for (int i = 0 ; i < this.nbJoueurs; i++){
      this.joueurs.add(new Joueur());
    }
  }

  public void ajouterUnJoueur() {
    this.nbJoueurs++;
    this.joueurs.add(new Joueur());
  }

  public Joueur getStatJoueur(int numeroJoueur){
    if (numeroJoueur < 0 || numeroJoueur >= nbJoueurs)
      return null;
    return joueurs.get(numeroJoueur);
  }

  public Joueur getJoueurEnCours(){
    return joueurs.get(joueurEnCours);
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
  public void setActif(int colonne) {
    try {
      checkColumnError(colonne);
      boolean flag = true;
      int ligne = nbRows - 1;

      //on parcours toutes les cases. De bas en haut
      //on active la case la plus basse non activée tel un puissance 4 physique
      while(flag && ligne >= 0){
        if(lesCase[colonne][ligne].getAppartientA() == null){
          flag = false;
          lesCase[colonne][ligne].setAppartientA(joueurs.get(joueurEnCours));
          Tools.P("La case " + "c"+ colonne + "-l" + ligne + " appartient a Joueur " + lesCase[colonne][ligne]);
          ajouterCoupJoueurEnCour();
          checkIfWinner(colonne, ligne);
          avancerJoueurEnCour();
          setChanged();
          notifyObservers();
        }
        ligne--;
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /*
   * On se base sur la derniere case jouée du dernier joueur
   * on tourne autour, si 4 case -> "winner" = id du joueur;
   */
  //TODO : améliorer ce truc
  private void checkIfWinner(int colonne, int ligne) {
    Joueur joueurCaseInitiale = lesCase[colonne][ligne].getAppartientA();
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
        //Test de diagonal de haut gauche vers bas droite
        nbCaseMemeJoueur  = checkLigneColonneDiag(colonne - 1, ligne - 1, -1, -1, joueurCaseInitiale);
        nbCaseMemeJoueur += checkLigneColonneDiag(colonne + 1, ligne + 1,  1,  1, joueurCaseInitiale);
        nbCaseMemeJoueur++; //add the first case;
        if (!updateIfWinner(joueurCaseInitiale, nbCaseMemeJoueur)){
          //Test de diagonal de bas gauche vers haut droit
          nbCaseMemeJoueur  = checkLigneColonneDiag(colonne - 1, ligne + 1, -1,  1, joueurCaseInitiale);
          nbCaseMemeJoueur += checkLigneColonneDiag(colonne + 1, ligne - 1,  1, -1, joueurCaseInitiale);
          nbCaseMemeJoueur++; //add the first case;
          updateIfWinner(joueurCaseInitiale, nbCaseMemeJoueur);

        }
      }
    }
  }

  /*
   * Si il y a un gagnant : on met a jour les stats et on affiche le message et on renvoie TRUE
   * Sinon on renvoit false
   */
  private boolean updateIfWinner(Joueur joueurCaseInitiale, int nbCaseMemeJoueur) {
    if (nbCaseMemeJoueur >= nbCasePourGagner){
      miseAJourGagnantPerdant(joueurCaseInitiale);
      winner = joueurCaseInitiale ;
      Tools.P("Le joueur " + winner + " gagne avec " + nbCaseMemeJoueur + " cases");
      return true;
    }
    return false;
  }

  private int checkLigneColonneDiag(int colonne, int ligne, int stepCol, int stepRow, Joueur joueurAChecker){

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

  private void miseAJourGagnantPerdant(Joueur joueurGagnant) {
    joueurGagnant.setNbWinPlus1();
    for (int i = 0 ; i < nbJoueurs; i++){
      if(joueurs.get(i) != joueurGagnant)
        joueurs.get(i).setNbLoosePlus1();
    }
  }


  private void ajouterCoupJoueurEnCour() {
    joueurs.get(joueurEnCours).setNbCoupsPlus1();
  }

  /*
   * Passe la main au suivant. retourne au joueur numéro 1 apres le dernier joueur
   */
  private void avancerJoueurEnCour() {
    joueurEnCours++;
    if (joueurEnCours >= nbJoueurs){
      joueurEnCours = 0;
    }
  }

  private void checkColumnError(int colonne) throws Exception {
    if (colonne < 0 || colonne >= this.nbColumns){
      throw new Exception("Cette colonne n'existe pas : " + colonne);
    }
  }

  private boolean checkValidColRow(int column, int row){
    if (column < 0 || column >= this.nbColumns || row < 0 || row >= this.nbRows){
      Tools.P("Aucune case existante pour c" + column + " l" + row);
      return false;
    }
    return true;
  }

  /*
   * Renvoie null si inactif. Sinon renvoie l'ID du joueur possédant la case
   */
  public Joueur getEtat(int col, int row){
    if(checkValidColRow(col, row)){
      return lesCase[col][row].getAppartientA();
    }
    return null;
  }

  public void nouvellePartie() {
    this.lesCase = new Case[nbColumns][nbRows];
    this.joueurEnCours = 0;
    initGrid();
    setChanged();
    notifyObservers();
  }
}
