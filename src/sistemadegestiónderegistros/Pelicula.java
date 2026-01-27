/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadegestiónderegistros;

import java.util.Objects;

/**
 *
 * @author A.ARMIJOS
 */
public class Pelicula {

    private String titulo;

    private String director;

    private int anio;

    private String id;
    
    private int duracion;
    
    private String genero;
    
    private String sinopsis;
    
    private String poster;
    

    public Pelicula() {
    }

    public Pelicula(String titulo, String director, int anio, int duracion, String genero, String sinopsis, String poster) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.poster = poster;
    }
    
    public Pelicula(String titulo, String director, int anio, String id, int duracion, String genero, String sinopsis, String poster) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.id = id;
        this.duracion = duracion;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.poster = poster;
    }
    

    /**
     * Get the value of poster
     *
     * @return the value of poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Set the value of poster
     *
     * @param poster new value of poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    
// hashCode y equals sin campo ID
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.titulo);
        hash = 43 * hash + Objects.hashCode(this.director);
        hash = 43 * hash + this.anio;
        hash = 43 * hash + this.duracion;
        hash = 43 * hash + Objects.hashCode(this.genero);
        hash = 43 * hash + Objects.hashCode(this.sinopsis);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pelicula other = (Pelicula) obj;
        if (this.anio != other.anio) {
            return false;
        }
        if (this.duracion != other.duracion) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.director, other.director)) {
            return false;
        }
        if (!Objects.equals(this.genero, other.genero)) {
            return false;
        }
        return Objects.equals(this.sinopsis, other.sinopsis);
    }

   


    @Override
    public String toString() {
        return "- " + titulo ;
    }
    
    

    /**
     * Get the value of sinopsis
     *
     * @return the value of sinopsis
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Set the value of sinopsis
     *
     * @param sinopsis new value of sinopsis
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }


    /**
     * Get the value of genero
     *
     * @return the value of genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Set the value of genero
     *
     * @param genero new value of genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }


    /**
     * Get the value of duracion
     *
     * @return the value of duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Set the value of duracion
     *
     * @param duracion new value of duracion
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the value of anio
     *
     * @return the value of anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Set the value of anio
     *
     * @param anio new value of anio
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Get the value of director
     *
     * @return the value of director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Set the value of director
     *
     * @param director new value of director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Get the value of titulo
     *
     * @return the value of titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Set the value of titulo
     *
     * @param titulo new value of titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
