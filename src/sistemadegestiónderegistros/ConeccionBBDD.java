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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author C.MORAGA
 */
public class ConeccionBBDD {

    Connection conexion;

    Statement stmt;
    ResultSet rs;
    ArrayList<Pelicula> peliculas = new ArrayList<>();

    public ConeccionBBDD() {
    }

    public boolean crearBBDD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
            stmt = conexion.createStatement();
            int executeUpdate = stmt.executeUpdate("create database if not exists peliculas");
            int executeUpdate1 = stmt.executeUpdate("use peliculas");
            int executeUpdate2 = stmt.executeUpdate("create table if not exists pelicula (titulo varchar(50), director varchar(50), anio year,id integer primary key auto_increment not null, duracion integer, genero varchar(50), sinopsis varchar(500), poster varchar(500));");
            int executeUpdate3 = stmt.executeUpdate("create table if not exists fecha (ultimaActualizacion date);");
            // Solo insertar fecha si la tabla está vacía (primera vez)
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM fecha");
            if (rs.next() && rs.getInt(1) == 0) {
                guardarFecha(LocalDate.now());  // Primera inicialización
            }

            return true;
        } catch (ClassNotFoundException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }

    public ArrayList<Pelicula> findAll() {
        peliculas.clear();
        try {
            rs = stmt.executeQuery("select * from pelicula");

            while (rs.next()) {
                Pelicula pelicula = new Pelicula(rs.getString("titulo"), rs.getString("director"), rs.getInt("anio"), rs.getString("id"), rs.getInt("duracion"), rs.getString("genero"), rs.getString("sinopsis"), rs.getString("poster"));
                peliculas.add(pelicula);
            }
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return peliculas;
    }

    public void agregaDesdeListado(Pelicula pelicula) {
        ArrayList<Pelicula> array = findAll();
        boolean encontrada = false;
        for (Pelicula p : array) {
            if (p.equals(pelicula)) {
                encontrada = true;
            }
        }
        if (!encontrada) {
            insertarPelicula(pelicula);
        }
    }

    public boolean guardarFecha(LocalDate fecha) {
        try {
            int rs = stmt.executeUpdate("INSERT INTO fecha (`ultimaActualizacion`) VALUES ('" + fecha + "')");
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }

    public LocalDate obtenerFechaBBDD() {
        try {
            // Consultar la última fecha guardada (asume tabla con un solo registro o usa ORDER BY/LIMIT)
            ResultSet rs = stmt.executeQuery("SELECT ultimaActualizacion FROM fecha ORDER BY ultimaActualizacion DESC LIMIT 1");

            if (rs.next()) {
                // Obtener la fecha como String (MySQL devuelve formato yyyy-MM-dd)
                String fechaStr = rs.getString("ultimaActualizacion");

                // Convertir a LocalDate
                return LocalDate.parse(fechaStr);
            }
            return null;

        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return null;
        }
    }

    public boolean borrarPelicula(Pelicula pelicula) {
        try {
            int executeUpdate = stmt.executeUpdate("DELETE FROM `peliculas`.`pelicula` WHERE (`id` = '" + pelicula.getId() + "')");
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }

    public boolean insertarPelicula(Pelicula pelicula) {
        try {
            int rs = stmt.executeUpdate("INSERT INTO pelicula (`titulo`, `director`, `anio`, `duracion`, `genero`, `sinopsis`, `poster`) VALUES ('" + pelicula.getTitulo() + "', '" + pelicula.getDirector() + "', " + pelicula.getAnio() + ", '" + pelicula.getDuracion() + "', '" + pelicula.getGenero() + "', '" + pelicula.getSinopsis() + "', '" + pelicula.getPoster() + "')");
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
    public boolean actualizarPelicula(Pelicula pelicula) {
        try {
            stmt.executeUpdate("UPDATE `pelicula` SET `titulo` = '" + pelicula.getTitulo() + "', `director` = '" + pelicula.getDirector() + "', `anio` = " + pelicula.getAnio() + ", `duracion` = '" + pelicula.getDuracion() + "', `genero` = '" + pelicula.getGenero() + "', `sinopsis` = '" + pelicula.getSinopsis() + "', `poster` = '" + pelicula.getPoster() + "' WHERE (`id` = '" + pelicula.getId() + "')");
            return true;
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }

    public void cerrarBD() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.getLogger(ConeccionBBDD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
