package prositJAVA_BDD;

import java.sql.*;
import java.io.*;
// affiche le contenu d'une base système ARTICLES
public class rqClavierUpdate{
static final String DB="statistiquesdb"; // base de données à exploiter
public static void main(String arg[]){
Connection connect=null; // connexion avec la base
Statement S=null; // objet d'émission des requêtes
ResultSet RS=null; // table résultat d'une requête
String sqlUpdate; // texte de la requête SQL de mise à jour
int nbLignes; // nb de lignes affectées par une mise à jour
// création d'un flux d'entrée clavier
BufferedReader in=null;
try{
in=new BufferedReader(new InputStreamReader(System.in));
} catch(Exception e){
erreur("erreur lors de l'ouverture du flux clavier ("+e+")",3);
}
try{
// connexion à la base
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
System.out.println("Connexion avec la base " + DB + " établie");
// création d'un objet Statement
S=connect.createStatement();
// boucle d'exécution des requêtes SQL tapées au clavier
System.out.print("Requête : ");
sqlUpdate=in.readLine();
while(!sqlUpdate.equals("fin")){
// exécution de la requête
nbLignes=S.executeUpdate(sqlUpdate);
// suivi
System.out.println(nbLignes + " ligne(s) ont été mises à jour");
// requête suivante
System.out.print("Requête : ");
sqlUpdate=in.readLine();
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
System.out.println("Base " + DB + " fermée");
// fermeture flux clavier
in.close();
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe