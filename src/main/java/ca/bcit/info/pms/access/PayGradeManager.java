package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.info.pms.model.PayLevel;

@Dependent
@Stateless
public class PayGradeManager implements Serializable
{
	@PersistenceContext( unitName = "pms-persistence-unit" )
	private EntityManager em;

	public PayLevel find( final int id )
	{
		return em.find( PayLevel.class, id );
	}

	public void remove( final PayLevel payLevel )
	{
		PayLevel pl = find( payLevel.getId() );
		em.remove( pl );
	}

	public void merge( final PayLevel payLevel )
	{
		em.merge( payLevel );
	}

	public void persist( final PayLevel payLevel )
	{
		em.persist( payLevel );
	}

	public void persist( final Map< String, BigDecimal > map, int year )
	{
		Set< Map.Entry< String, BigDecimal >> entrySet = map.entrySet();

		for ( Map.Entry< String, BigDecimal > entry : entrySet )
		{
			String str = entry.getKey();
			BigDecimal value = entry.getValue();

			PayLevel payLevel = getPayLevel( str, year );

			if ( payLevel != null )
			{
				// merge
				payLevel.setName( str );
				payLevel.setYear( year );
				payLevel.setCost( value );
				em.merge( payLevel );

			} else
			{
				// create new
				payLevel = new PayLevel();
				payLevel.setName( str );
				payLevel.setYear( year );
				payLevel.setCost( value );
				em.persist( payLevel );
			}

			payLevel = null;

		}

	}

	@SuppressWarnings( "unchecked" )
	public List< PayLevel > getAllPayLevels()
	{
		ArrayList< PayLevel > list = new ArrayList< PayLevel >();
		Query qry = em.createNativeQuery( "SELECT * FROM PayGrade ORDER BY year ASC, name DESC",
		        PayLevel.class );

		try
		{
			list = ( ArrayList< PayLevel > ) qry.getResultList();
		} catch ( Exception e )
		{
			// do nothing ?
		}
		return list;
	}

	public double getCost( final String payLevelName, final int year )
	{
		Query query = em
		        .createNativeQuery(
		                "select * " + "from paygrade " + "where name = :payName " + "and year = :year",
		                PayLevel.class ).setParameter( "payName", payLevelName ).setParameter( "year", year );
		PayLevel payLevel = ( PayLevel ) query.getSingleResult();
		BigDecimal cost = payLevel.getCost();

		return cost.doubleValue();
	}

	public PayLevel getPayLevel( final String name, final int year )
	{

		Query query = em
		        .createNativeQuery(
		                "select * " + "from paygrade " + "where name = :payName " + "and year = :year",
		                PayLevel.class ).setParameter( "payName", name ).setParameter( "year", year );
		PayLevel payLevel = ( PayLevel ) query.getSingleResult();

		return payLevel;
	}

}
