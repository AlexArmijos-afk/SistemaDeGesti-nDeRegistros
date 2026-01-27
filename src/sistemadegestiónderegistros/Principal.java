package sistemadegestiónderegistros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author A.ARMIJOS
 */
public class Principal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Principal.class.getName());
    private ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
    private Boolean conectado;
    private ConeccionBBDD peliculasBBDD;
    private LocalDateTime fecha = LocalDateTime.now();

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        
        peliculasBBDD = new ConeccionBBDD();
        
        conectado = peliculasBBDD.crearBBDD();
             
        if(conectado){
            System.out.println("Conexión a la BBDD exitosa");
            peliculas = peliculasBBDD.findAll();
        }else{
            System.out.println("ERROR al conectarse la BBDD. Se trabajará en local");
            leerXML(new File("peliculas.xml"));
        }
        
        cargarLista(peliculas);
    }

    public void leerXML(File archivo) {
    
    try {
        if (archivo.exists()) {

            SAXParserFactory.newInstance().newSAXParser().parse(
                    archivo,
                    new DefaultHandler() {

                Pelicula actual;

                boolean id;           // AÑADIDO
                boolean titulo;
                boolean director;
                boolean anio;
                boolean duracion;
                boolean genero;
                boolean sinopsis;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                    if (qName.equalsIgnoreCase("pelicula")) {
                        actual = new Pelicula();
                    }

                    // AÑADIDO: Detectar etiqueta ID
                    if (qName.equalsIgnoreCase("ID")) {
                        id = true;
                    }

                    if (qName.equalsIgnoreCase("Titulo")) {
                        titulo = true;
                    }

                    if (qName.equalsIgnoreCase("Director")) {
                        director = true;
                    }

                    if (qName.equalsIgnoreCase("Anio")) {
                        anio = true;
                    }

                    if (qName.equalsIgnoreCase("Duracion")) {
                        duracion = true;
                    }

                    if (qName.equalsIgnoreCase("Genero")) {
                        genero = true;
                    }

                    if (qName.equalsIgnoreCase("Sinopsis")) {
                        sinopsis = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {

                    String texto = new String(ch, start, length).trim();
                    if (texto.isEmpty()) {
                        return;
                    }

                    // AÑADIDO: Leer y asignar ID
                    if (id) {
                        actual.setId(texto);
                        id = false;
                    }

                    if (titulo) {
                        actual.setTitulo(texto);
                        titulo = false;
                    }

                    if (director) {
                        actual.setDirector(texto);
                        director = false;
                    }

                    if (anio) {
                        actual.setAnio(Integer.parseInt(texto));
                        anio = false;
                    }

                    if (duracion) {
                        actual.setDuracion(Integer.parseInt(texto));
                        duracion = false;
                    }

                    if (genero) {
                        actual.setGenero(texto);
                        genero = false;
                    }

                    if (sinopsis) {
                        actual.setSinopsis(texto);
                        sinopsis = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    boolean encontrada = false;
                    if (qName.equalsIgnoreCase("pelicula")) {
                        if (conectado) {
                            //comparar con la bbdd
                            peliculasBBDD.agregaDesdeListado(actual);
                            peliculas = peliculasBBDD.findAll();
                        } else {
                            //comparar con arraylist
                            for (Pelicula p : peliculas) {
                                if (p.equals(actual)) {
                                    encontrada = true;
                                }
                            }
                            if (!encontrada) {
                                peliculas.add(actual);
                            }
                        }
                    }
                }
            }
            );
        } else {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo " + archivo.getName());
            System.out.println("Error");
        }
    } catch (ParserConfigurationException ex) {
        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SAXException ex) {
        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bNuevaPeli = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlPelicula = new javax.swing.JList<>();
        bImportar = new javax.swing.JButton();
        bExportar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        bNuevaPeli.setText("Nueva Peli");
        bNuevaPeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNuevaPeliActionPerformed(evt);
            }
        });

        jlPelicula.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jlPelicula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPeliculaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jlPelicula);

        bImportar.setText("Importar");
        bImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImportarActionPerformed(evt);
            }
        });

        bExportar.setText("Exportar");
        bExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExportarActionPerformed(evt);
            }
        });

        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });

        jLabel1.setText("Mis peliculas");

        tBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tBuscarKeyTyped(evt);
            }
        });

        jLabel2.setText("Buscar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(bEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bImportar)
                    .addComponent(bExportar))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bNuevaPeli)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tBuscar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bNuevaPeli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bImportar)
                    .addComponent(bEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bExportar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bNuevaPeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNuevaPeliActionPerformed
        DetallePelicula ventanaDetallePelicula = new DetallePelicula(this, true);
        ventanaDetallePelicula.setVisible(true);

        if (conectado) {
            //comparar con la bbdd
            peliculasBBDD.agregaDesdeListado(ventanaDetallePelicula.getPeliculaEnvio());
            peliculas = peliculasBBDD.findAll();
        } else {
            if (!peliculas.contains(ventanaDetallePelicula.getPeliculaEnvio())) {
                peliculas.add(ventanaDetallePelicula.getPeliculaEnvio());
            }

        }

        cargarLista(peliculas);
    }//GEN-LAST:event_bNuevaPeliActionPerformed

    private void jlPeliculaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPeliculaMouseClicked
        if (evt.getClickCount() == 2) {
            DetallePelicula p = new DetallePelicula(this, true, peliculas.get(jlPelicula.getSelectedIndex()));
            p.setVisible(true);
            System.out.println(p.getEliminar());

            if (conectado) {
                if (!p.getEliminar()) {
                    peliculas.set(jlPelicula.getSelectedIndex(), p.getPeliculaEnvio());
                    peliculasBBDD.actualizarPelicula(p.getPeliculaEnvio());
                    cargarLista(peliculas);
                } else {
                    peliculas.remove(jlPelicula.getSelectedIndex());
                    peliculasBBDD.borrarPelicula(p.getPeliculaEnvio());
                    cargarLista(peliculas);
                }
            } else {
                if (!p.getEliminar()) {
                    peliculas.set(jlPelicula.getSelectedIndex(), p.getPeliculaEnvio());
                    cargarLista(peliculas);
                } else {
                    peliculas.remove(jlPelicula.getSelectedIndex());
                    cargarLista(peliculas);
                }
            }
        }
    }//GEN-LAST:event_jlPeliculaMouseClicked

    public void cargarLista(ArrayList<Pelicula> array) {
        DefaultListModel modelo = new DefaultListModel();
        modelo.setSize(0);
        modelo.addAll(array);

        jlPelicula.setModel(modelo);
    }

    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        Pelicula p = peliculas.get(jlPelicula.getSelectedIndex());
        peliculasBBDD.borrarPelicula(p);
        peliculas.remove(jlPelicula.getSelectedIndex());
        cargarLista(peliculas);
    }//GEN-LAST:event_bEliminarActionPerformed

    private void bImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImportarActionPerformed
        JFileChooser selector = new JFileChooser(".");
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int respuesta = selector.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            if (selector.getSelectedFile().getName().substring(selector.getSelectedFile().getName().indexOf("."), selector.getSelectedFile().getName().length()).equals(".xml")) {
                leerXML(selector.getSelectedFile());
            }else if (selector.getSelectedFile().getName().substring(selector.getSelectedFile().getName().indexOf("."), selector.getSelectedFile().getName().length()).equals(".json")) {
                leerJSON(selector.getSelectedFile());
            }
            cargarLista(peliculas);
        }
    }//GEN-LAST:event_bImportarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        escribirXML(new File("peliculas.xml"));
        peliculasBBDD.guardarFecha(fecha);
    }//GEN-LAST:event_formWindowClosing

    private void bExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExportarActionPerformed
        JFileChooser selector = new JFileChooser(".");
        selector.setSelectedFile(new File("prueba"));
        int respuesta = selector.showSaveDialog(this);
        
//        if (respuesta == JFileChooser.APPROVE_OPTION) {
//            File archivoNuevo = new File(selector.getSelectedFile() + ".xml");
//            escribirXML(archivoNuevo);
//        }
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            File archivoNuevo = new File(selector.getSelectedFile() + ".json");
            escribirJSON(archivoNuevo);
        }
    }//GEN-LAST:event_bExportarActionPerformed

    private void tBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBuscarKeyTyped
        
    }//GEN-LAST:event_tBuscarKeyTyped

    private void tBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBuscarKeyReleased
        ArrayList<Pelicula> encontradas = new ArrayList<>();
        for (Pelicula encontrada : peliculas) {
            if (encontrada.getTitulo().toLowerCase().contains(tBuscar.getText().toLowerCase())) {
                encontradas.add(encontrada);
                cargarLista(encontradas);
            }
        }
    }//GEN-LAST:event_tBuscarKeyReleased

    public void escribirXML(File archivoSalida) {
    try {

        File aPeliculas = archivoSalida;
        if (aPeliculas.createNewFile()) {
            System.out.println("Archivo " + archivoSalida.getName() + " creado");
            JOptionPane.showMessageDialog(this, "Archivo " + archivoSalida.getName() + " creado");
        }

        Document dpeliculas = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element raiz = dpeliculas.createElement("Peliculas");
        raiz.setAttribute("fechaGeneracion", fecha.toString());
        dpeliculas.appendChild(raiz);
        
        for (Pelicula p : peliculas) {
            Element pelicula = dpeliculas.createElement("pelicula");
            raiz.appendChild(pelicula);

            // AÑADIDO: Escribir ID
            Element id = dpeliculas.createElement("ID");
            id.setTextContent(String.valueOf(p.getId()));
            pelicula.appendChild(id);

            Element titulo = dpeliculas.createElement("Titulo");
            titulo.setTextContent(p.getTitulo());
            pelicula.appendChild(titulo);

            Element director = dpeliculas.createElement("Director");
            director.setTextContent(p.getDirector());
            pelicula.appendChild(director);

            Element anio = dpeliculas.createElement("Anio");
            anio.setTextContent(String.valueOf(p.getAnio()));
            pelicula.appendChild(anio);

            Element duracion = dpeliculas.createElement("Duracion");
            duracion.setTextContent(String.valueOf(p.getDuracion()));
            pelicula.appendChild(duracion);

            Element genero = dpeliculas.createElement("Genero");
            genero.setTextContent(p.getGenero());
            pelicula.appendChild(genero);

            Element sinopsis = dpeliculas.createElement("Sinopsis");
            sinopsis.setTextContent(p.getSinopsis());
            pelicula.appendChild(sinopsis);
        }
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(dpeliculas), new StreamResult(aPeliculas));
        
    } catch (IOException ex) {
        new JOptionPane("Error al crear el archivo " + archivoSalida.getName(), JOptionPane.ERROR_MESSAGE).setVisible(true);
    } catch (ParserConfigurationException | TransformerException ex) {
        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public void escribirJSON(File archivoSalida){   
        try {
            File aPeliculas = archivoSalida; 
        
            if (aPeliculas.createNewFile()) {
                System.out.println("Archivo " + archivoSalida.getName() + " creado");
                JOptionPane.showMessageDialog(this, "Archivo " + archivoSalida.getName() + " creado");
            }

            ObjectMapper mapeador = new ObjectMapper();
            mapeador.writeValue(archivoSalida, peliculas);
            
        } catch (JsonProcessingException ex) {
            System.getLogger(Principal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }catch (IOException ex) {
            System.getLogger(Principal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        
    }
    }
    
    public void leerJSON(File archivoEntrada){
        if(archivoEntrada.exists()){
            ObjectMapper mapeador = new ObjectMapper();
            try {
                List<Map<String, Object>> pelis = mapeador.readValue(archivoEntrada, new TypeReference<List<Map<String, Object>>>(){});
                for (Map<String, Object> peli : pelis) {
                    String titulo = (String) peli.get("titulo");
                    String director = (String) peli.get("director");
                    int anio = (int) peli.get("anio");
                    String id = (String) peli.get("id");
                    int duracion = (int) peli.get("duracion");
                    String genero =(String) peli.get("genero");
                    String sinopsis = (String) peli.get("sinopsis");
                    
                    Pelicula peliNueva = new Pelicula(titulo, director, anio, id, duracion, genero, sinopsis);
                    
                    if (conectado) {
                            //comparar con la bbdd
                            peliculasBBDD.agregaDesdeListado(peliNueva);
                            peliculas = peliculasBBDD.findAll();
                    } else {
                        if (!peliculas.contains(peliNueva)) {
                            peliculas.add(peliNueva);
                        }
                    }
                }
                
            } catch (IOException ex) {
                System.getLogger(Principal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }else {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo " + archivoEntrada.getName());
            System.out.println("Error");
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Principal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bExportar;
    private javax.swing.JButton bImportar;
    private javax.swing.JButton bNuevaPeli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> jlPelicula;
    private javax.swing.JTextField tBuscar;
    // End of variables declaration//GEN-END:variables
}
