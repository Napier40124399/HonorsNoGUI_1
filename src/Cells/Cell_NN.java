package Cells;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import NetworkFinal.Network;
import NetworkFinal.Remember;

/**
 * <h1>Cell_NN</h1> Neural network designed to mimic the hardcoded cell logic.
 * 
 * @author James Taylor
 *
 */
public class Cell_NN extends Cellular implements Cell
{
	private Network nextGenNet;
	private Network w;
	private ArrayList<Remember> memory = new ArrayList<Remember>();
	private Float accepted = 0f;
	private Float total = 0f;

	public Cell_NN()
	{
		w = new Network(20);
	}

	@Override
	public void setFitness()
	{
		int i = 0;
		for (Cell ce : neighboors)
		{
			Float decisionOP = ce.getOut(coords);
			Float decisionME = w.think(memory.get(i).getMem());

			memory.get(i).save(decisionME);
			memory.get(i).saveOP(decisionOP);
			memory.get(i).normalize();

			fitness += -0.75f * decisionME + 1.75f * decisionOP + 2.25f;

			i++;
		}
	}

	@Override
	public void setNewGeneration()
	{
		/**
		 * One of two ways, either manualy copy a network or use cloneable.
		 */

		daddys = new ArrayList<Cell>();
		double old = fitness;
		for (Cell ce : neighboors)
		{
			if (ce.getPower() > old)
			{
				daddys.clear();
				daddys.add(ce);
				old = ce.getPower();
			} else if (ce.getPower() == old)
			{
				daddys.add(ce);
			}
		}
		if (daddys.size() > 0)
		{
			int tt = (int) (Math.random() * (daddys.size()));
			nextGenNet = daddys.get(tt).getNetwork();
		}
		daddys.clear();
	}
	
	@Override
	public void updateCell()
	{
		accepted = new Float(fitness);
		total = new Float(neighboors.size());
		Float tt = accepted / total;
		Float temp = (tt / 4.75f) / bridge.getItPerGen();
		Float fff = 250 * temp;
		int iii = (int) (fff * 1);

		c = new Color(iii, iii, iii);
		
		if(!nextGenNet.equals(null))
		{
			if(!nextGenNet.equals(w))
			{
				w = nextGenNet.deepClone();
			}
		}
		
		fitness = 0;
	}

	@Override
	public void mutationLogic()
	{
		// More to come!!
		w.mutate();
	}
	
	@Override
	public void resetMemory()
	{
		for (Remember r : memory)
		{
			r.reset();
		}
	}
	
	@Override
	public Float getOut(Point2D cods)
	{
		for (int i = 0; i < neighboors.size(); i++)
		{
			if (neighboors.get(i).getCoords().equals(coords))
			{
				return w.think(memory.get(i).getMem());
			}
		}
		// Failsafe
		return new Float(0);
	}
	
	@Override
	public Network getNetwork()
	{
		return w;
	}

	@Override
	public void setNeighboors()
	{
		super.setNeighboors();
		for (Cell c : neighboors)
		{
			memory.add(new Remember(3));
		}
	}
}
