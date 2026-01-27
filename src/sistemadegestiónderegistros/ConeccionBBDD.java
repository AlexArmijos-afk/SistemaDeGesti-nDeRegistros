/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadegestiónderegistros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author C.MORAGA
 */
public class ConeccionBBDD {
    Connection conexion;
    
    Statement stmt;
    ResultSet rs;
    ArrayList<Pelicula> peliculas = new ArrayList<>();
    
    public ConeccionBBDD(){
    }
    
    public boolean crearBBDD(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
            stmt = conexion.createStatement();
            int executeUpdate = stmt.executeUpdate("create database if not exists peliculas");
            int executeUpdate1 = stmt.executeUpdate("use peliculas");
            int executeUpdate2 = stmt.executeUpdate("create table if not exists pelicula (titulo varchar(50), director varchar(50), anio year,id integer primary key auto_increment not null, duracion integer, genero varchar(50), sinopsis varchar(500));");
            int executeUpdate3 = stmt.executeUpdate("create table if not exists fecha (ultimaActualizacion datetime);");
            
            return true;
        } catch (ClassNotFoundException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
    public ArrayList<Pelicula> findAll(){
        peliculas.clear();
        try {
            rs = stmt.executeQuery("select * from pelicula");
            
            while(rs.next()){
                Pelicula pelicula = new Pelicula(rs.getString("titulo"), rs.getString("director"), rs.getInt("anio"), rs.getString("id"), rs.getInt("duracion"), rs.getString("genero"), rs.getString("sinopsis"));
                peliculas.add(pelicula);
            }
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return peliculas;
    }
    
    public void agregaDesdeListado(Pelicula pelicula){
        ArrayList<Pelicula> array = findAll();
        boolean encontrada=false;
        for (Pelicula p : array) {
            if(p.equals(pelicula)){
                encontrada= true;
            }
        }
        if(!encontrada){
            insertarPelicula(pelicula);
        }  
    }

    public boolean guardarFecha(LocalDateTime fecha){
        try {
            int rs = stmt.executeUpdate("INSERT INTO fecha (`ultimaActualizacion`) VALUES ('" + fecha + "')");
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }  
    }
    
    public boolean borrarPelicula(Pelicula pelicula){
        try {
            int executeUpdate = stmt.executeUpdate("DELETE FROM `peliculas`.`pelicula` WHERE (`id` = '"+pelicula.getId()+"')");   
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
    public boolean insertarPelicula(Pelicula pelicula){
        try {
            int rs = stmt.executeUpdate("INSERT INTO pelicula (`titulo`, `director`, `anio`, `duracion`, `genero`, `sinopsis`) VALUES ('"+ pelicula.getTitulo() +"', '"+ pelicula.getDirector()+"', "+ pelicula.getAnio()+", '"+ pelicula.getDuracion()+"', '"+ pelicula.getGenero()+"', '"+ pelicula.getSinopsis()+"')");
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
//    public void comparar(Pelicula pelicula){
//        for (Pelicula pelicula : array) {
//            if()
//        }
//    }
    
    
    public String findAlumnoById(int id){   //tal vez debería generar un alumno y devolverlo
        String infoAlumno = null;
        try {            
            try {            
                rs = stmt.executeQuery("select * from alumno where id = " + id);  
            } catch (SQLException ex) {
                System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            while(rs.next()){
                infoAlumno = "Id: " + rs.getInt("id") + " - Nombre: " + rs.getString("nombre") + " - Edad: " + rs.getInt("edad");
            }
            
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return infoAlumno;
    }
    
    public boolean actualizarPelicula(Pelicula pelicula){
        try {
            stmt.executeUpdate("UPDATE `pelicula` SET `titulo` = '"+pelicula.getTitulo()+"', `director` = '"+pelicula.getDirector()+"', `anio` = "+pelicula.getAnio()+", `duracion` = '"+pelicula.getDuracion()+"', `genero` = '"+pelicula.getGenero()+"', `sinopsis` = '"+pelicula.getSinopsis()+"' WHERE (`id` = '"+pelicula.getId()+"')");
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
    public void cerrarBD(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
