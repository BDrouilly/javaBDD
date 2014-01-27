package prositJAVA_BDD;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//affiche le contenu d'une base syst�me ARTICLES
public class rqSQL_clavier{
static final String DB="statistiquesdb"; // base de donn�es � exploiter
public static void main(String arg[]){
Connection connect=null; // connexion avec la base
Statement S=null; // objet d'�mission des requ�tes
ResultSet RS=null; // table r�sultat d'une requ�te
String select; // texte de la requ�te SQL select
int nbColonnes; // nb de colonnes du ResultSet
//cr�ation d'un flux d'entr�e clavier
BufferedReader in=null;
try{
in=new BufferedReader(new InputStreamReader(System.in));
} catch(Exception e){
erreur("erreur lors de l'ouverture du flux clavier ("+e+")",3);
}
try{
//connexion � la base
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
System.out.println("Connexion avec la base " + DB + " �tablie");
//cr�ation d'un objet Statement
S=connect.createStatement();
//boucle d'ex�cution des requ�tes SQL tap�es au clavier
System.out.print("Requ�te : ");
select=in.readLine();
while(!select.equals("fin")){

//ex�cution de la requ�te
RS=S.executeQuery(select);
//nombre de colonnes
nbColonnes=RS.getMetaData().getColumnCount();
//exploitation de la table des r�sultats
System.out.println("R�sultats obtenus\n\n");
while(RS.next()){ // tant qu'il y a une ligne � exploiter
//on l'affiche � l'�cran
for(int i=1;i<nbColonnes;i++)
System.out.print(RS.getString(i)+",");
System.out.println(RS.getString(nbColonnes));
}// ligne suivante
//requ�te suivante
System.out.print("Requ�te : ");
select=in.readLine();
}// while
} catch (Exception e){
erreur("Erreur " + e,2);
}
//fermeture de la base et du flux d'entr�e
try{
connect.close();
System.out.println("Base " + DB + " ferm�e");
in.close();
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe