package ca.bcit.info.pms.service;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.bcit.info.pms.model.Employee;

import java.io.Serializable;

@Stateless
@LocalBean
public class EmployeeManager implements Serializable
{

   private static final long serialVersionUID = -1L;
   
   @PersistenceContext
   EntityManager em;
   
   /**
    * Persist
    */
   public void persist( Employee employee ){}
   
   /**
    * Merge
    */
   public void merge( Employee employee ){}
   
   /**
    * Find
    */
   public Employee find(){
	   
	   return null;
   }
   
   
   
   
}