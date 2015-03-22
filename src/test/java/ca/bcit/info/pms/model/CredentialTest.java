package ca.bcit.info.pms.model;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.bcit.info.pms.util.PasswordHash;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class CredentialTest {
    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage(Credential.class.getPackage())
                .addPackage(PasswordHash.class.getPackage())
                .addAsResource("test-persistence.xml",
                        "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");   
        return war;
    }
    
    @Inject
    Credential cred;
    
    @Test
    public void should_pass() {
        cred = new Credential();
        String pass = "password";

        cred.setPassword(pass);
        
        assertTrue(cred.getPassword().equals(pass));
    }
}
