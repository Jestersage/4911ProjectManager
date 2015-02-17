package ca.bcit.info.pms.access;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.bcit.info.pms.model.Credential;

public class EmployeeManagerTest {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Inject
    private EmployeeManager empMgr;
    
    public void should_get_all_credentials() {
        List<Credential> credsA = new ArrayList<Credential>();
        List<Credential> credsB = new ArrayList<Credential>();
        
        Credential a = new Credential("a", "a");
        Credential b = new Credential("b", "b");
        Credential c = new Credential("c", "c");
        
        credsA.add(a);
        credsA.add(b);
        credsA.add(c);
        
        credsB.add(a);
        
        empMgr.persistCredential(c);
        
       // credsB = empMgr.getAllCredentials();
        
        assertEquals(credsA, credsB);
    }

}
