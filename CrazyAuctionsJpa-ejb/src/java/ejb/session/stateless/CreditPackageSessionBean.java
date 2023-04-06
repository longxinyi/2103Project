/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author chiaangyong
 */
@Stateless
public class CreditPackageSessionBean implements CreditPackageSessionBeanRemote, CreditPackageSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;

   

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
}
