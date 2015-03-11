package ca.bcit.info.pms.access;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Project;

@Stateless
@LocalBean
public class ProjectManager implements Serializable{

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(ProjectManager.class);
    
    public void updateProject(Project newProject){
        
    }
}
