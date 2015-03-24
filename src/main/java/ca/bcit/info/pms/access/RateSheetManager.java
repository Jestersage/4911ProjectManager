package ca.bcit.info.pms.access;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.bcit.info.pms.model.RateSheet;

@Dependent
@Stateless
public class RateSheetManager implements Serializable
{
	@PersistenceContext( unitName = "pms-persistence-unit" )
	private EntityManager em;

	public RateSheet find( final int id )
	{
		return em.find( RateSheet.class, id );
	}

	public void remove( final RateSheet level )
	{
		RateSheet pl = find( level.getId() );
		em.remove( pl );
	}

	public void merge( final RateSheet level )
	{
		em.merge( level );
	}

	public void persist( final RateSheet level )
	{
		em.persist( level );
	}

}
