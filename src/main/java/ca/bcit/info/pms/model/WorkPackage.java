package ca.bcit.info.pms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Work package. Sub-item of project. Can be nested.
 */
@Entity
@Table( name = "WorkPackage" )
public class WorkPackage implements Serializable
{
    @Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "packageID" )
	private Integer id=0;

    
    private String employeeID;
    
	@ManyToOne
	@JoinColumn( name = "projectID", updatable = false )
	//@NotNull( message = "Project ID can not be null" )
	//@Size( max = 20, message = "Project ID cannot be longer than 20" )
	private Project project;

	@NotNull( message = "Package Number can not be null" )
	@Size( max = 20, message = "Package Number cannot be longer than 20" )
	private String packageNum;

    @ManyToMany
    @JoinTable(name = "WorkAssignment",
            joinColumns = {@JoinColumn(name = "packageID")},
            inverseJoinColumns = {@JoinColumn(name = "employeeID")})
//	@NotNull( message = "Employee ID can not be null" )
//	@Size( max = 10, message = "Employee ID cannot be longer than 10" )
	private Set< Employee > employees;

	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parentwpID", nullable=true)
	private WorkPackage parentWP;

//	@OneToMany( mappedBy = "parentWP" )
//	private Collection< WorkPackage > childWP;

	@Column( name = "packageName" )
	@Size( max = 20, message = "Package name cannot be longer than 20" )
	private String name;

	@Column( name = "packageDesc" )
	@Size( max = 20, message = "Package description cannot be longer than 20" )
	private String description;

    @Enumerated(EnumType.ORDINAL)
    @NotNull( message = "Work package status cannot be null.")
	private ProjectStatus status;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinColumn(name = "budgetID", nullable=true)
    private Budget budget;
    
    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinColumn(name = "engBudgetID", nullable=true)
    private EngineerBudget engineerBudget;
    
    @Column( name = "isLead" )
	private Boolean leaf = false;
    
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

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

    @Transient
    private double totalEngineerCost;
    
    @Transient
    private double engineerManDays;
    
    @Transient
    private String completionPercentage;
    
	public String getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(String completionPercentage) {
		this.completionPercentage = completionPercentage + "% complete";
	}

	public double getTotalEngineerCost() {
		return totalEngineerCost;
	}

	public void setTotalEngineerCost(double totalEngineerCost) {
		this.totalEngineerCost = totalEngineerCost;
	}

	public double getEngineerManDays() {
		return engineerManDays;
	}

	public void setEngineerManDays(double engineerManDays) {
		this.engineerManDays = engineerManDays;
	}

	public WorkPackage() {
		this.budget = new Budget();
		this.project = new Project();
		this.engineerBudget = new EngineerBudget();
		
	}

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

	public Integer getId()
	{
		return this.id;
	}

	public void setId( final Integer id )
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
//
//	public Collection< WorkPackage > getChildWP()
//	{
//		return childWP;
//	}
//
//	public void setChildWP( Collection< WorkPackage > childWP )
//	{
//		this.childWP = childWP;
//	}

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

   

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	@Override
    public String toString() {
        return "WorkPackage [packageNum=" + packageNum + ", name=" + name
                + ", status=" + status + "]";
    }

	public EngineerBudget getEngineerBudget() {
		return engineerBudget;
	}

	public void setEngineerBudget(EngineerBudget engineerBudget) {
		this.engineerBudget = engineerBudget;
	}

}