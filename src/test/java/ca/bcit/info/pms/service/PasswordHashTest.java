package ca.bcit.info.pms.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;





import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
//  import org.jboss.arquillian.container.test.api.Deployment;
//  import org.jboss.arquillian.junit.Arquillian;
//  import org.jboss.shrinkwrap.api.ShrinkWrap;
//  import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PasswordHashTest {
  
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(PasswordHash.class);
        return jar;
    }


    @Test
    public void should_validate_passwords() {
        try {
            String password = "password";

            String hash = PasswordHash.createHash(password);
            
            assertTrue(PasswordHash.validatePassword(password, hash));
        } catch (Exception ex) {
            System.out.println("ERROR: "+ex);
        }
    }
    
    @Test
    public void should_create_new_hash() {
        try {
            String password = "password";
            String hash = PasswordHash.createHash(password);
            String secondHash = PasswordHash.createHash(password);
            
            assertFalse(hash.equals(secondHash));
        } catch (Exception ex) {
            System.out.println("ERROR: "+ex);
        }
    }
    
    @Test
    public void should_not_validate_password() {
        try {
            String password = "password";
            String hash = PasswordHash.createHash(password);
            String wrongPassword = "drowssap";
            
            assertFalse(PasswordHash.validatePassword(wrongPassword, hash));
        } catch (Exception ex) {
            System.out.println("ERROR: "+ex);
        }
    }
}
