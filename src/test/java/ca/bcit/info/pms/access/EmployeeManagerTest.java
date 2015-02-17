package ca.bcit.info.pms.access;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;

import ca.bcit.info.pms.model.Credential;

public class EmployeeManagerTest {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    EmployeeManager empMgr;
    
    @Test
    public void should_get_all_credentials() {
        List<Credential> creds = new ArrayList<Credential>();
        
        Credential a = new Credential("a", "a");
        Credential b = new Credential("b", "b");
        Credential c = new Credential("c", "c");
        
        creds.add(a);
        creds.add(b);
        creds.add(c);
        
        empMgr.persistCredential(a);
        empMgr.persistCredential(b);
        empMgr.persistCredential(c);
    }

}
