package prositJAVA_BDD;

import java.sql.*;
import java.io.*;
// affiche le contenu d'une base syst�me ARTICLES
public class test{
static final String DB="visiteur"; // base de donn�es � exploiter
public static void main(String arg[]){
Connection connect=null; // connexion avec la base
Statement S=null; // objet d'�mission des requ�tes
ResultSet RS=null; // table r�sultat d'une requ�te
try{
// connexion � la base
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
System.out.println("Connexion avec la base " + DB + " �tablie");
// cr�ation d'un objet Statement
S=connect.createStatement();
// ex�cution d'une requ�te select
RS=S.executeQuery("select * from " + DB);
// exploitation de la table des r�sultats
while(RS.next()){ // tant qu'il y a une ligne � exploiter
// on l'affiche � l'�cran
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
System.out.println("Base " + DB + " ferm�e");
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe