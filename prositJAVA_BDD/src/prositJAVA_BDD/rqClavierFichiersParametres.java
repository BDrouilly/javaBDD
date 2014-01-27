package prositJAVA_BDD;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// appel : pg PILOTE URL UID MDP
// se connecte à la base URL grâce à la classe JDBC PILOTE
// l'utilisateur UID est identifié par un mot de passe MDP
public class rqClavierFichiersParametres{
static String syntaxe="pg PILOTE URL UID MDP";
public static void main(String arg[]){
// vérification du nb d'arguments
if(arg.length<2 || arg.length>4)
erreur(syntaxe,1);
// init paramètres de la connexion
Connection connect=null;
String uid="";
String mdp="";
if(arg.length>=3) uid=arg[2];
if(arg.length==4) mdp=arg[3];
// autres données
Statement S=null; // objet d'émission des requêtes
ResultSet RS=null; // table résultat d'une requête d'interrogation
String sqlText; // texte de la requête SQL à exécuter

int nbLignes; // nb de lignes affectées par une mise à jour
int nbColonnes; // nb de colonnes d'un ResultSet
// création d'un flux d'entrée clavier
BufferedReader in=null;
try{
in=new BufferedReader(new InputStreamReader(System.in));
} catch(Exception e){
erreur("erreur lors de l'ouverture du flux clavier ("+e+")",3);
}
try{
// connexion à la base
Class.forName(arg[0]);
connect=DriverManager.getConnection(arg[1],uid,mdp);
System.out.println("Connexion avec la base " + arg[1] + " établie");
// création d'un objet Statement
S=connect.createStatement();
// boucle d'exécution des requêtes SQL tapées au clavier
System.out.print("Requête : ");
sqlText=in.readLine();
while(!sqlText.equals("fin")){
// exécution de la requête
try{
if(S.execute(sqlText)){
// on a obtenu un ResultSet - on l'exploite
RS=S.getResultSet();
// nombre de colonnes
nbColonnes=RS.getMetaData().getColumnCount();
// exploitation de la table des résultats
System.out.println("\nRésultats obtenus\n-----------------\n");
while(RS.next()){ // tant qu'il y a une ligne à exploiter
// on l'affiche à l'écran
for(int i=1;i<nbColonnes;i++)
System.out.print(RS.getString(i)+",");
System.out.println(RS.getString(nbColonnes));
}// ligne suivante du ResultSet
} else {
// c'était une requête de mise à jour
nbLignes=S.getUpdateCount();
// suivi
System.out.println(nbLignes + " ligne(s) ont été mises à jour");
}//if
} catch (Exception e){
System.out.println("Erreur " +e);
}
// requête suivante
System.out.print("\nNouvelle Requête : ");
sqlText=in.readLine();
}// while
} catch (Exception e){
erreur("Erreur " + e,2);
}
// fermeture de la base et du flux d'entrée
try{
// on libère les ressources liées à la base
RS.close();
S.close();
connect.close();
System.out.println("Base " + arg[1] + " fermée");
// fermeture flux clavier
in.close();
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe