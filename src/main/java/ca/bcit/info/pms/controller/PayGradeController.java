package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.PayGradeManager;
import ca.bcit.info.pms.model.PayLevel;

@Named( "payGradeController" )
@RequestScoped
public class PayGradeController implements Serializable
{

	@Inject
	private PayGradeManager payGradeManager;

	@Inject
	private PayLevel payLevel;

	private Map< String, BigDecimal > payLevelsMap;

	private List< PayLevel > payLevelList;

	private BigDecimal P1;
	private BigDecimal P2;
	private BigDecimal P3;
	private BigDecimal P4;
	private BigDecimal P5;
	private BigDecimal SS;
	private BigDecimal JS;
	private BigDecimal DS;

	private int year;

	PayGradeController()
	{
	}

	public PayLevel getPayLevel()
	{
		return payLevel;
	}

	public void setPayLevel( PayLevel payLevel )
	{
		this.payLevel = payLevel;
	}

	public BigDecimal getP1()
	{
		return P1;
	}

	public void setP1( BigDecimal p1 )
	{
		P1 = p1;
	}

	public BigDecimal getP2()
	{
		return P2;
	}

	public void setP2( BigDecimal p2 )
	{
		P2 = p2;
	}

	public BigDecimal getP3()
	{
		return P3;
	}

	public void setP3( BigDecimal p3 )
	{
		P3 = p3;
	}

	public BigDecimal getP4()
	{
		return P4;
	}

	public void setP4( BigDecimal p4 )
	{
		P4 = p4;
	}

	public BigDecimal getP5()
	{
		return P5;
	}

	public void setP5( BigDecimal p5 )
	{
		P5 = p5;
	}

	public BigDecimal getSS()
	{
		return SS;
	}

	public void setSS( BigDecimal sS )
	{
		SS = sS;
	}

	public BigDecimal getJS()
	{
		return JS;
	}

	public void setJS( BigDecimal jS )
	{
		JS = jS;
	}

	public BigDecimal getDS()
	{
		return DS;
	}

	public void setDS( BigDecimal dS )
	{
		DS = dS;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear( int year )
	{
		this.year = year;
	}

	public String save()
	{
		payLevelsMap = new HashMap< String, BigDecimal >();

		payLevelsMap.put( "P1", P1 );
		payLevelsMap.put( "P2", P2 );
		payLevelsMap.put( "P3", P3 );
		payLevelsMap.put( "P4", P4 );
		payLevelsMap.put( "P5", P5 );
		payLevelsMap.put( "SS", SS );
		payLevelsMap.put( "DS", DS );
		payLevelsMap.put( "JS", JS );

		payGradeManager.persist( payLevelsMap, year );

		return "editPayLevels";

	}

	public Map< String, BigDecimal > getPayLevelsMap()
	{
		return payLevelsMap;
	}

	public void setPayLevelsMap( Map< String, BigDecimal > payLevelsMap )
	{
		this.payLevelsMap = payLevelsMap;
	}

	public List< PayLevel > getPayLevelList()
	{

		payLevelList = payGradeManager.getAllPayLevels();

		return payLevelList;
	}

	public void setPayLevelList( List< PayLevel > payLevelList )
	{
		this.payLevelList = payLevelList;
	}

}
