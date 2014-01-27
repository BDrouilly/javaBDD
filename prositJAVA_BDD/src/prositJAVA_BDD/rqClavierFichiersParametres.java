package prositJAVA_BDD;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// appel : pg PILOTE URL UID MDP
// se connecte � la base URL gr�ce � la classe JDBC PILOTE
// l'utilisateur UID est identifi� par un mot de passe MDP
public class rqClavierFichiersParametres{
static String syntaxe="pg PILOTE URL UID MDP";
public static void main(String arg[]){
// v�rification du nb d'arguments
if(arg.length<2 || arg.length>4)
erreur(syntaxe,1);
// init param�tres de la connexion
Connection connect=null;
String uid="";
String mdp="";
if(arg.length>=3) uid=arg[2];
if(arg.length==4) mdp=arg[3];
// autres donn�es
Statement S=null; // objet d'�mission des requ�tes
ResultSet RS=null; // table r�sultat d'une requ�te d'interrogation
String sqlText; // texte de la requ�te SQL � ex�cuter

int nbLignes; // nb de lignes affect�es par une mise � jour
int nbColonnes; // nb de colonnes d'un ResultSet
// cr�ation d'un flux d'entr�e clavier
BufferedReader in=null;
try{
in=new BufferedReader(new InputStreamReader(System.in));
} catch(Exception e){
erreur("erreur lors de l'ouverture du flux clavier ("+e+")",3);
}
try{
// connexion � la base
Class.forName(arg[0]);
connect=DriverManager.getConnection(arg[1],uid,mdp);
System.out.println("Connexion avec la base " + arg[1] + " �tablie");
// cr�ation d'un objet Statement
S=connect.createStatement();
// boucle d'ex�cution des requ�tes SQL tap�es au clavier
System.out.print("Requ�te : ");
sqlText=in.readLine();
while(!sqlText.equals("fin")){
// ex�cution de la requ�te
try{
if(S.execute(sqlText)){
// on a obtenu un ResultSet - on l'exploite
RS=S.getResultSet();
// nombre de colonnes
nbColonnes=RS.getMetaData().getColumnCount();
// exploitation de la table des r�sultats
System.out.println("\nR�sultats obtenus\n-----------------\n");
while(RS.next()){ // tant qu'il y a une ligne � exploiter
// on l'affiche � l'�cran
for(int i=1;i<nbColonnes;i++)
System.out.print(RS.getString(i)+",");
System.out.println(RS.getString(nbColonnes));
}// ligne suivante du ResultSet
} else {
// c'�tait une requ�te de mise � jour
nbLignes=S.getUpdateCount();
// suivi
System.out.println(nbLignes + " ligne(s) ont �t� mises � jour");
}//if
} catch (Exception e){
System.out.println("Erreur " +e);
}
// requ�te suivante
System.out.print("\nNouvelle Requ�te : ");
sqlText=in.readLine();
}// while
} catch (Exception e){
erreur("Erreur " + e,2);
}
// fermeture de la base et du flux d'entr�e
try{
// on lib�re les ressources li�es � la base
RS.close();
S.close();
connect.close();
System.out.println("Base " + arg[1] + " ferm�e");
// fermeture flux clavier
in.close();
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe