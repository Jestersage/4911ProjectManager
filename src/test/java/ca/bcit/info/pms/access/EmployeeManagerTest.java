package ca.bcit.info.pms.access;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.service.EmployeeService;
import ca.bcit.info.pms.service.impl.EmployeeServiceImpl;

@RunWith(Arquillian.class)
public class EmployeeManagerTest {
    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage(EmployeeManager.class.getPackage())
                .addPackage(Credential.class.getPackage())
                .addPackage(Employee.class.getPackage())
                .addAsResource("test-persistence.xml",
                        "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println("STUFF: " + war.toString(true) + " ::/STUFF");

        return war;
    }

    @Inject
    EmployeeManager empMngr;

    @PersistenceContext
    EntityManager em;

    @Test
    public void should_persist_credential() {
        Credential cred = new Credential("bob", "password");
        empMngr.persistCredential(cred);
        assertTrue(em.find(Credential.class, cred.getUsername()) != null);
    }
}
