package prositJAVA_BDD;

import java.sql.*;
import java.io.*;
// affiche le contenu d'une base syst�me ARTICLES
public class rqClavierUpdate{
static final String DB="statistiquesdb"; // base de donn�es � exploiter
public static void main(String arg[]){
Connection connect=null; // connexion avec la base
Statement S=null; // objet d'�mission des requ�tes
ResultSet RS=null; // table r�sultat d'une requ�te
String sqlUpdate; // texte de la requ�te SQL de mise � jour
int nbLignes; // nb de lignes affect�es par une mise � jour
// cr�ation d'un flux d'entr�e clavier
BufferedReader in=null;
try{
in=new BufferedReader(new InputStreamReader(System.in));
} catch(Exception e){
erreur("erreur lors de l'ouverture du flux clavier ("+e+")",3);
}
try{
// connexion � la base
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
System.out.println("Connexion avec la base " + DB + " �tablie");
// cr�ation d'un objet Statement
S=connect.createStatement();
// boucle d'ex�cution des requ�tes SQL tap�es au clavier
System.out.print("Requ�te : ");
sqlUpdate=in.readLine();
while(!sqlUpdate.equals("fin")){
// ex�cution de la requ�te
nbLignes=S.executeUpdate(sqlUpdate);
// suivi
System.out.println(nbLignes + " ligne(s) ont �t� mises � jour");
// requ�te suivante
System.out.print("Requ�te : ");
sqlUpdate=in.readLine();
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
System.out.println("Base " + DB + " ferm�e");
// fermeture flux clavier
in.close();
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe