package ca.bcit.info.pms.model;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.bcit.info.pms.util.PasswordHash;
import static org.junit.Assert.assertFalse;
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
    @Named("credential")
    Credential cred;
    
    @Test
    public void should_pass() {
        //TODO Create actual test method
        String pass = "password";
        cred = new Credential("username", pass);
        
        assertFalse(cred.getPassword().equals(pass));
    }
}
