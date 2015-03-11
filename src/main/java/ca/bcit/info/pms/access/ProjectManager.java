package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Project;

@Stateless
@LocalBean
public class ProjectManager implements Serializable{

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(ProjectManager.class);
    
    /*
     * persists a project to the database
     */
    public void persist(Project newProject){
        Project match = entityManager.find(Project.class, newProject.getId());
        
        if (match != null) {
            logger.warn("Record(Project) already exist! Project Id: " + newProject.getId() + ", " + newProject.getName());
        }
        else {
            entityManager.persist(newProject);
            logger.info("Project added: " + newProject.getId() + ", " + newProject.getName());
        }
    }
    
    /*
     * gets all the projects from the database
     */
    public List<Project> getAllProjects(){
        CriteriaQuery<Project> criteria = this.entityManager.getCriteriaBuilder().createQuery(Project.class);
        return this.entityManager.createQuery(criteria.select(criteria.from(Project.class))).getResultList();
    }
    
    /*
     * finds a project by its id
     */
    public Project findByProjectId(final String projectId){
        return this.entityManager.find(Project.class, projectId);
    }
    
    /*
     * finds a project by its name
     */
    public Project findByProjectName(final String projectName){
        return this.entityManager.find(Project.class, projectName);
    }
    
    /*
     * deletes a project by its id
     */
    public String delete(final String id) {

        try {
            Project deletableEntity = findByProjectId(id);

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();
            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(e.getMessage()));
            return null;
        }
    }
    
    /*
     * updates a project in the database
     */
    public String updateProject(Project project) {
        try {
            if (project.getId() == null) {
                persist(project);               
                return "search?faces-redirect=true";
            } else {
                this.entityManager.merge(project);
                return "view?faces-redirect=true&id=" + project.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(e.getMessage()));
            return null;
        }
    }
}
