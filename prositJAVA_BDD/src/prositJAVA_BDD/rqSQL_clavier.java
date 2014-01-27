package prositJAVA_BDD;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//affiche le contenu d'une base système ARTICLES
public class rqSQL_clavier{
static final String DB="statistiquesdb"; // base de données à exploiter
public static void main(String arg[]){
Connection connect=null; // connexion avec la base
Statement S=null; // objet d'émission des requêtes
ResultSet RS=null; // table résultat d'une requête
String select; // texte de la requête SQL select
int nbColonnes; // nb de colonnes du ResultSet
//création d'un flux d'entrée clavier
BufferedReader in=null;
try{
in=new BufferedReader(new InputStreamReader(System.in));
} catch(Exception e){
erreur("erreur lors de l'ouverture du flux clavier ("+e+")",3);
}
try{
//connexion à la base
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
System.out.println("Connexion avec la base " + DB + " établie");
//création d'un objet Statement
S=connect.createStatement();
//boucle d'exécution des requêtes SQL tapées au clavier
System.out.print("Requête : ");
select=in.readLine();
while(!select.equals("fin")){

//exécution de la requête
RS=S.executeQuery(select);
//nombre de colonnes
nbColonnes=RS.getMetaData().getColumnCount();
//exploitation de la table des résultats
System.out.println("Résultats obtenus\n\n");
while(RS.next()){ // tant qu'il y a une ligne à exploiter
//on l'affiche à l'écran
for(int i=1;i<nbColonnes;i++)
System.out.print(RS.getString(i)+",");
System.out.println(RS.getString(nbColonnes));
}// ligne suivante
//requête suivante
System.out.print("Requête : ");
select=in.readLine();
}// while
} catch (Exception e){
erreur("Erreur " + e,2);
}
//fermeture de la base et du flux d'entrée
try{
connect.close();
System.out.println("Base " + DB + " fermée");
in.close();
} catch (Exception e){}
}// main
public static void erreur(String msg, int exitCode){
System.err.println(msg);
System.exit(exitCode);
}
}// classe