/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;
import entity.CreditPackage;
import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreditPackageNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.UpdateCreditPackageException;

/**
 *
 * @author chiaangyong
 */
@Stateless
public class CreditPackageSessionBean implements CreditPackageSessionBeanRemote, CreditPackageSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCreditPackage(CreditPackage creditPackage){
        em.persist(creditPackage);
        em.flush();
        return creditPackage.getCreditPackageId();
    }
   

    
    @Override
    public Long createNewCreditPackage(BigDecimal creditPrice, String creditPackageType, Boolean isActive){
        CreditPackage creditPackage = new CreditPackage(creditPrice, creditPackageType, isActive);
        em.persist(creditPackage);
        em.flush();
        
        return creditPackage.getCreditPackageId();
    }
    
    @Override
    public List<CreditPackage> retrieveCreditTransactionHistory(Customer customer) throws CreditTransactionHistoryNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CreditPackage c ");
        //query.setParameter("inCustomer", customer);
        
        try
        {
            return (List<CreditPackage>)query.getResultList();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CreditTransactionHistoryNotFoundException("Credit Transaction History " + " does not exist! Please Register!");
        }
    };
    
    public List<CreditPackage> retrieveCreditPackage() throws CreditTransactionHistoryNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CreditPackage c ");
        //query.setParameter("inCustomer", customer);
        
        try
        {
            return (List<CreditPackage>)query.getResultList();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CreditTransactionHistoryNotFoundException("Credit Transaction History " + " does not exist! Please Register!");
        }
    };
    
    public CreditPackage retrieveCreditPackageById(Long creditPackageId) throws CreditPackageNotFoundException 
    {
        Query query = em.createQuery("SELECT c FROM CreditPackage c WHERE c.creditPackageId = :inCreditPackageId");
        query.setParameter("inCreditPackageId", creditPackageId);
        
        try
        {
            return (CreditPackage)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CreditPackageNotFoundException("Credit Package " + creditPackageId + " does not exist! Please create!");
        }
    }
    
    public void updateCreditPackage(CreditPackage creditPackage) throws CreditPackageNotFoundException, UpdateCreditPackageException {
        if(creditPackage.getCreditPackageId() != null)
        {
            CreditPackage creditPackageToUpdate = retrieveCreditPackageById(creditPackage.getCreditPackageId());
            
            if(creditPackageToUpdate.getCreditPackageId().equals(creditPackage.getCreditPackageId()))
            {
                creditPackageToUpdate.setCreditPrice(creditPackage.getCreditPrice());
                creditPackageToUpdate.setIsActive(creditPackage.getIsActive());
                
            }
            else
            {
                throw new UpdateCreditPackageException("Credit Price of credit package record to be updated does not match the existing record");
            }
        }
        else
        {
            throw new CreditPackageNotFoundException("Credit Package not found!");
        }
        
    }
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
}
