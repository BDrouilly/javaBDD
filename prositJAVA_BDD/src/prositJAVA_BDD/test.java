package prositJAVA_BDD;

import java.sql.*;
import java.io.*;
// affiche le contenu d'une base système ARTICLES
public class test{
static final String DB="visiteur"; // base de données à exploiter
public static void main(String arg[]){
Connection connect=null; // connexion avec la base
Statement S=null; // objet d'émission des requêtes
ResultSet RS=null; // table résultat d'une requête
try{
// connexion à la base
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
System.out.println("Connexion avec la base " + DB + " établie");
// création d'un objet Statement
S=connect.createStatement();
// exécution d'une requête select
RS=S.executeQuery("select * from " + DB);
// exploitation de la table des résultats
while(RS.next()){ // tant qu'il y a une ligne à exploiter
// on l'affiche à l'écran
System.out.println(
RS.getString("adresseIP")+","+
RS.getString("navigateur")+","+
RS.getString("OS"));
}// ligne suivante
} catch (Exception e){
erreur("Erreur " + e,2);
}
// fermeture de la base
try{
connect.close();
System.out.println("Base " + DB + " fermée");
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe