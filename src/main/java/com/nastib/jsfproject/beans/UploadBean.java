
package com.nastib.jsfproject.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.nastib.jsfproject.entities.Fichier;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@ManagedBean
@RequestScoped
public class UploadBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int TAILLE_TAMPON = 10240;
    private Fichier           fichier;
    private static final String CHEMIN = "/NetBeansProjects/JavaEE/jsfproject/src/main/webapp/resources/uploadfile/";
    
    // Initialisation du bean fichier
    public UploadBean() {
        fichier = new Fichier();
    }

    public void envoyer() throws IOException, BeanValidationException {
        String nomFichier = FilenameUtils.getName( fichier.getContenu().getName() );
        String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getContenu().getSize() );
        String typeFichier = fichier.getContenu().getContentType();
        //byte[]  contenuFichier = fichier.getContenu().getBytes();
        InputStream contenuFichier = fichier.getContenu().getInputStream();

        /*
         * Effectuer ici l'enregistrement du contenu du fichier sur le disque,
         * ou dans la BDD (accompagné du type du contenu, éventuellement), ou
         * tout autre traitement souhaité...
         */
        /*
         * Si le fichier est bien une image, alors son en-tête MIME
         * commence par la chaîne "image"
         */
        if ( typeFichier.startsWith( "image" ) ) {
            try {
                /* Écriture du fichier sur le disque */
                ecrireFichier(contenuFichier, nomFichier, CHEMIN );
                FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                        String.format( "Fichier '%s', de taille '%s' et de type '%s' envoyé avec succès !",
                                nomFichier, tailleFichier, typeFichier ) ) );                
            } catch (BeanValidationException ex) {
                throw new BeanValidationException( ex.getMessage() );
            }
        } else {
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                String.format( "Le fichier envoyé doit être une image. " )));
        }
        

    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier( Fichier fichier ) {
        this.fichier = fichier;
    }
    
    /*
     * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
     * sur le disque, dans le répertoire donné et avec le nom donné.
     */
    private void ecrireFichier( InputStream contenuFichier, String nomFichier, String chemin )
            throws BeanValidationException {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenuFichier, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } catch ( Exception e ) {
            throw new BeanValidationException( "Erreur lors de l'écriture du fichier sur le disque." );
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }    
}