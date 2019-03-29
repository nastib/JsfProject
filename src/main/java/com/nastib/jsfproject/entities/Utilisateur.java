
package com.nastib.jsfproject.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NamedQueries({@NamedQuery(name = "Utilisateur.selectEmail", 
                           query="SELECT u FROM Utilisateur u WHERE u.email = :email")
            })
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "id")
    private Long      id;
    
    @NotNull (message = "Veuillez saisir une adresse email")
    @Pattern( regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Merci de saisir une adresse mail valide" )
    @Column(name = "email")
    private String    email;
    
    @NotNull (message = "Veuillez saisir un mot de passe")
    @Size( min = 3, message = "Le mot de passe doit contenir au moins 3 caractères" )
    //@Pattern(regexp = ".*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*", message = "Le mot de passe saisi n'est pas assez sécurisé")
    @Column( name = "mot_de_passe" )
    private String    motDePasse;
    
    @NotNull (message = "Veuillez saisir un nom d'utilisateur")
    @Size( min = 3, message = "Le nom d'utilisateur doit contenir au moins 3 caractères" )
    @Column(name = "nom")
    private String    nom;
    
    @Column( name = "date_inscription" )
    private Timestamp dateInscription;

    public Long getId() {
        return id;
    }
    public void setId( Long id ) {
        this.id = id;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setMotDePasse( String motDePasse ) {
        this.motDePasse = motDePasse;
    }
    public String getMotDePasse() {
        return motDePasse;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }

    public Timestamp getDateInscription() {
        return dateInscription;
    }
    public void setDateInscription( Timestamp dateInscription ) {
        this.dateInscription = dateInscription;
    }
}
