package ca.bcit.info.pms.access;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.LoginCredential;
import ca.bcit.info.pms.util.PasswordHash;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class EmployeeManagerTest {
    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage(EmployeeManager.class.getPackage())
                .addPackage(Credential.class.getPackage())
                .addPackage(LoginCredential.class.getPackage())
                .addPackage(PasswordHash.class.getPackage())
                .addPackage(Employee.class.getPackage())
                .addAsResource("test-persistence.xml",
                        "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

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
