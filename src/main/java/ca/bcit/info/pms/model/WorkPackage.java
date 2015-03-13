package ca.bcit.info.pms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table( name = "WorkPackage" )
public class WorkPackage implements Serializable
{
    @Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "packageID", updatable = false, nullable = false )
	private int id;

	@ManyToOne
	@JoinColumn( name = "projectID", updatable = false )
	@NotNull( message = "Project ID can not be null" )
	@Size( max = 20, message = "Project ID cannot be longer than 20" )
	private Project project;

	@NotNull( message = "Package Number can not be null" )
	@Size( max = 20, message = "Package Number cannot be longer than 20" )
	private String packageNum;

    @ManyToMany
    @JoinTable(name = "WorkAssignment",
            joinColumns = {@JoinColumn(name = "packageID")},
            inverseJoinColumns = {@JoinColumn(name = "employeeID")})
	@NotNull( message = "Employee ID can not be null" )
	@Size( max = 10, message = "Employee ID cannot be longer than 10" )
	private Set< Employee > employees;

	@ManyToOne
    @JoinColumn(name = "parentwpID")
	private WorkPackage parentWP;

	@OneToMany( mappedBy = "parentWP" )
	private Collection< WorkPackage > childWP;

	@Column( name = "packageName" )
	@Size( max = 20, message = "Package name cannot be longer than 20" )
	private String name;

	@Column( name = "packageDesc" )
	@Size( max = 20, message = "Package description cannot be longer than 20" )
	private String description;

    @Enumerated(EnumType.ORDINAL)
    @NotNull( message = "Work package status cannot be null.")
	private ProjectStatus status;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "budgetID")
    private Budget budget;
    
    @Transient
    private int totalBudget;
    
    @Transient
    private double totalCost;
    
    
    @Transient
    private double totalActualCost;

    @Transient
    private double actualManDays;
    
    @Transient
    private double estimateManDays;
    
    @Transient
    private double totalEstimateCost;
    
    @Transient
    private String varianceManDays;
    
    @Transient
    private String totalVarianceCost;

	public String getVarianceManDays() {
		return varianceManDays;
	}

	public void setVarianceManDays(String varianceManDays) {
		this.varianceManDays = varianceManDays;
	}

	public String getTotalVarianceCost() {
		return totalVarianceCost;
	}

	public void setTotalVarianceCost(String totalVarianceCost) {
		this.totalVarianceCost = totalVarianceCost;
	}

	public double getEstimateManDays() {
		return estimateManDays;
	}

	public void setEstimateManDays(double estimateManDays) {
		this.estimateManDays = estimateManDays;
	}

	public double getTotalEstimateCost() {
		return totalEstimateCost;
	}

	public void setTotalEstimateCost(double totalEstimateCost) {
		this.totalEstimateCost = totalEstimateCost;
	}

	public double getTotalActualCost() {
		return totalActualCost;
	}

	public void setTotalActualCost(double totalActualCost) {
		this.totalActualCost = totalActualCost;
	}


    public double getActualManDays() {
		return actualManDays;
	}

	public void setActualManDays(double actualManDays) {
		this.actualManDays = actualManDays;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public int getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(int totalBudget) {
		this.totalBudget = totalBudget;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId( final int id )
	{
		this.id = id;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject( Project project )
	{
		this.project = project;
	}

	public String getPackageNum()
	{
		return packageNum;
	}

	public void setPackageNum( String packageNum )
	{
		this.packageNum = packageNum;
	}

	public Set< Employee > getEmployees()
	{
		return employees;
	}

	public void setEmployees( Set< Employee > employees )
	{
		this.employees = employees;
	}

	public WorkPackage getParentWP()
	{
		return parentWP;
	}

	public void setParentWP( WorkPackage parentWP )
	{
		this.parentWP = parentWP;
	}

	public Collection< WorkPackage > getChildWP()
	{
		return childWP;
	}

	public void setChildWP( Collection< WorkPackage > childWP )
	{
		this.childWP = childWP;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String packageName)
	{
		this.name = packageName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String packageDesc)
	{
		this.description = packageDesc;
	}

	public ProjectStatus getStatus()
	{
		return status;
	}

	public void setStatus( ProjectStatus status )
	{
		this.status = status;
	}

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		if ( !( obj instanceof WorkPackage ) )
		{
			return false;
		}
		WorkPackage other = ( WorkPackage ) obj;
		if ( id >= 0 )
		{
			if ( id != other.id )
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id < 0) ? 0 : (id + "").hashCode());
		return result;
	}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WorkPackage{");
        sb.append("id=").append(id);
        sb.append(", project=").append(project.getId());
        sb.append(", packageNum='").append(packageNum).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", status=").append(status.toString());
        sb.append(", budget=").append(budget);

        sb.append(", employees={");
        for(Employee e : employees) {
            sb.append(e.getCredential().getUsername()).append(",");
        }
        sb.append('}');

        if (parentWP != null) {
            sb.append(", parentWP=").append(parentWP.getPackageNum());
        }

        sb.append(", childWP={");
        for (WorkPackage wp : childWP) {
            sb.append(wp.getPackageNum()).append(",");
        }
        sb.append('}');
        sb.append('}');
        return sb.toString();
    }
}
