package prositJAVA_BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//CTRL + SHIFT + O pour générer les imports
public class Connect {
	public static void main(String[] args) throws SQLException {
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
	 
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/statistiquesdb","root", "13853211");
	 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		
		//Prepared Statement
		
		 	
		
		 Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
         
	      String query = "SELECT adresseIP, OS FROM visiteur";         
	      ResultSet res = state.executeQuery(query);
	      int i = 1;         
	         
	      System.out.println("\n\t---------------------------------------");
	      System.out.println("\tLECTURE STANDARD.");
	      System.out.println("\t---------------------------------------");
	      
	      while(res.next()){
		        System.out.println("\tNom : "+res.getString("adresseIP") +" \t prénom : "+res.getString("OS"));
		        //On regarde si on se trouve sur la dernière ligne du résultat
		        if(res.isLast())
		          System.out.println("\t\t* DERNIER RESULTAT !\n");
		        i++;
		      }
		         
		      //Une fois la lecture terminée, on contrôle si on se trouve bien à l'extérieur des lignes de résultat
		      if(res.isAfterLast())
		        System.out.println("\tNous venons de terminer !\n");
		         
		      System.out.println("\t---------------------------------------");
		      System.out.println("\tLecture en sens contraire.");
		      System.out.println("\t---------------------------------------");
		         
		      //On se trouve alors à la fin
		      //On peut parcourir le résultat en sens contraire
		      while(res.previous()){
		        System.out.println("\tNom : "+res.getString("adresseIP") +" \t prénom : "+res.getString("OS"));

		        //On regarde si on se trouve sur la première ligne du résultat
		        if(res.isFirst())
		          System.out.println("\t\t* RETOUR AU DEBUT !\n");
		      }
		         
		      //On regarde si on se trouve avant la première ligne du résultat
		      if(res.isBeforeFirst())
		        System.out.println("\tNous venons de revenir au début !\n");
		         
		      System.out.println("\t---------------------------------------");
		      System.out.println("\tAprès positionnement absolu du curseur à la place N° "+ i/2 + ".");
		      System.out.println("\t---------------------------------------");
		      //On positionne le curseur sur la ligne i/2
		      //Peu importe où on se trouve
		      res.absolute(i/2);
		      while(res.next())
		        System.out.println("\tNom : "+res.getString("adresseIP") +" \t prénom : "+ res.getString("OS"));
		         
		      System.out.println("\t---------------------------------------");
		      System.out.println("\tAprès positionnement relatif du curseur à la place N° "+(i-(i-2)) + ".");
		      System.out.println("\t---------------------------------------");
		      //On place le curseur à la ligne actuelle moins i-2
		      //Si on n'avait pas mis de signe moins, on aurait avancé de i-2 lignes 
		      res.relative(-(i-2));
		      while(res.next())
		        System.out.println("\tNom : "+res.getString("adresseIP") +" \t prénom : "+res.getString("OS"));
	      
	      
	      res.close();
	      state.close();
		
		
		
		
		
		
	  }
	}