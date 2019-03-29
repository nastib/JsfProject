
package com.nastib.jsfproject.dao;

import com.nastib.jsfproject.entities.Utilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(mappedName="UtilisateurDao")
public class UtilisateurDaoImpl  implements UtilisateurDao {

    private static final String PARAM_EMAIL = "email";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "JsfProject_PU" )
    private EntityManager       em;

    // Enregistrement d'un nouvel utilisateur  
    @Override
    public void creer( Utilisateur utilisateur ) throws DaoException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DaoException( e.getMessage() );
        }
    }

    // Recherche d'un utilisateur à partir de son adresse email
    @Override
    public Utilisateur trouver( String email ) throws DaoException {
       
        try {
            Query query = em.createNamedQuery( "Utilisateur.selectEmail");
            query.setParameter( PARAM_EMAIL, email );
            return (Utilisateur) query.getSingleResult();  
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DaoException( e.getMessage() );
        }
    }
}