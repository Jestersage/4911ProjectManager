package ca.bcit.info.pms.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ca.bcit.info.pms.model.TimesheetRow;

/**
 * Backing bean for TimesheetRow entities.
 * <p/>
 * This class provides CRUD functionality for all TimesheetRow entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TimesheetRowBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving TimesheetRow entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private TimesheetRow timesheetRow;

   public TimesheetRow getTimesheetRow()
   {
      return this.timesheetRow;
   }

   public void setTimesheetRow(TimesheetRow timesheetRow)
   {
      this.timesheetRow = timesheetRow;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(unitName = "pms-persistence-unit", type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      this.conversation.setTimeout(1800000L);
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
         this.conversation.setTimeout(1800000L);
      }

      if (this.id == null)
      {
         this.timesheetRow = this.example;
      }
      else
      {
         this.timesheetRow = findById(getId());
      }
   }

   public TimesheetRow findById(Long id)
   {

      return this.entityManager.find(TimesheetRow.class, id);
   }

   /*
    * Support updating and deleting TimesheetRow entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.timesheetRow);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.timesheetRow);
            return "view?faces-redirect=true&id=" + this.timesheetRow.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         TimesheetRow deletableEntity = findById(getId());

         this.entityManager.remove(deletableEntity);
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching TimesheetRow entities with pagination
    */

   private int page;
   private long count;
   private List<TimesheetRow> pageItems;

   private TimesheetRow example = new TimesheetRow();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public TimesheetRow getExample()
   {
      return this.example;
   }

   public void setExample(TimesheetRow example)
   {
      this.example = example;
   }

   public String search()
   {
      this.page = 0;
      return null;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<TimesheetRow> root = countCriteria.from(TimesheetRow.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<TimesheetRow> criteria = builder.createQuery(TimesheetRow.class);
      root = criteria.from(TimesheetRow.class);
      TypedQuery<TimesheetRow> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<TimesheetRow> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<TimesheetRow> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back TimesheetRow entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<TimesheetRow> getAll()
   {

      CriteriaQuery<TimesheetRow> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(TimesheetRow.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(TimesheetRow.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final TimesheetRowBean ejbProxy = this.sessionContext.getBusinessObject(TimesheetRowBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((TimesheetRow) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private TimesheetRow add = new TimesheetRow();

   public TimesheetRow getAdd()
   {
      return this.add;
   }

   public TimesheetRow getAdded()
   {
      TimesheetRow added = this.add;
      this.add = new TimesheetRow();
      return added;
   }
}
