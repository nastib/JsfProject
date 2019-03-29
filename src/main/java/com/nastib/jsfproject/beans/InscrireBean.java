
package com.nastib.jsfproject.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.nastib.jsfproject.dao.UtilisateurDao;
import com.nastib.jsfproject.entities.Utilisateur;
import javax.faces.bean.ViewScoped;

@ManagedBean
//@RequestScoped est un gachi pour gérer un formulaire ajaxisé d'ou il faut lui préférer @ViewScoped
@ViewScoped
public class InscrireBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Utilisateur  utilisateur;

    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private UtilisateurDao    utilisateurDao;
    
    // Initialisation de l'entité utilisateur
    public InscrireBean() {
        utilisateur = new Utilisateur();
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    public void inscrire() {
        utilisateur.setDateInscription( new Timestamp( System.currentTimeMillis() ));
        utilisateurDao.creer( utilisateur );
        FacesMessage message = new FacesMessage( "Succès de l'inscription !" );
        FacesContext.getCurrentInstance().addMessage( null, message );
    }

}