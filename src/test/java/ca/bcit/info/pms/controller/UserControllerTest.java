package ca.bcit.info.pms.controller;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.service.EmployeeService;
import ca.bcit.info.pms.service.impl.EmployeeServiceImpl;

//@RunWith(Arquillian.class)
public class UserControllerTest {
    /*
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(UserController.class.getPackage())
            .addPackage(EmployeeService.class.getPackage())
            .addPackage(Credential.class.getPackage())
            .addPackage(EmployeeServiceImpl.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/
    
    @Inject
    UserController userController;
    
  //  @Test
    public void should_pass() {
        int x = 3;
        
        assertEquals(x, 3);
    }
}
