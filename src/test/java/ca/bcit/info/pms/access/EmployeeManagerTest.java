package ca.bcit.info.pms.access;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
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
        List<Credential> credsB = new ArrayList<Credential>();
        
        Credential a = new Credential("a", "a");
        Credential b = new Credential("b", "b");
        Credential c = new Credential("c", "c");
        
        creds.add(a);
        creds.add(b);
        creds.add(c);
        
        credsB.add(a);
        credsB.add(b);
        credsB.add(c);
        
        assertEquals(creds, credsB);
    }

}
