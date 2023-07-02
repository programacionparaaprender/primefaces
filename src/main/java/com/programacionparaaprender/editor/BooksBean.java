package com.programacionparaaprender.editor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;

@ManagedBean
@ViewScoped
public class BooksBean {
	private List<Books> books;
	
	@PostConstruct
    public void init() {
    	 Connection conexion=null; 
    	 books = new java.util.LinkedList<Books>();
  	     try {
  	          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  	          String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
  	            "databaseName=QuirkyBookProject;user=sa;password=123;";
  	          conexion = DriverManager.getConnection(connectionUrl);
  	        } catch (SQLException e) {
  	            System.out.println("SQL Exception: "+ e.toString());
  	        } catch (ClassNotFoundException cE) {
  	            System.out.println("Class Not Found Exception: "+ cE.toString());
  	        }
  	        String sql = "SELECT TOP (1000) [Id]\n" + 
  	        		"      ,[Name]\n" + 
  	        		"  FROM [dbo].[Books]";
  	        try (PreparedStatement cmd = conexion.prepareStatement(sql)) {
  	                ResultSet rs = cmd.executeQuery();
  	                
  	                while(rs.next())
  	                {
  	                	String name = rs.getString(2);
  	                	Integer id = rs.getInt(1);
  	                	Books temp = new Books(id, name);
  	                	books.add(temp);
  	                }
  	                conexion.close();
  	                
  	            }
  	        catch(Exception ex)
  	        {
  	        	
  	        }
    }
	
	public void setBooks(List<Books> books) {
		
	}
	public List<Books> getBooks() {
		return books;
	}
}
