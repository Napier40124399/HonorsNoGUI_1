package DataCenter;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import Cells.Cell;

/**
 * <h1>Bridge</h1> The bridge controls all the data running between threads.
 * This allows for each part of the system to communicate with another. In this
 * manner everything is accessbile from anywhere. Only drawback is all instances
 * of any class wanting to communicate need access to the same instance of this
 * class as all the others. <br>
 * Using singleton pattern may alleviate this somewhat but everything currently
 * works just fine.
 *
 * @param img
 *            is the image made in the {@link Logics.Draw draw} class, and
 *            displayed in the {@link DataCenter.Storage panel} via the event
 *            {@link Logics.ActionHandlers#BuildHandlers() "store.timer"}.
 * @param cells
 *            is an {@link ArrayList} containing all the cells in the
 *            simulation.
 * @param ready
 *            defines whether {@link Scenarios.Scenario the scenrio} is done
 *            preparing the simulation.
 * @param done
 *            defines whether {@link Scenarios.Scenario the scenario} is done
 *            with the simlation.
 * @param speed
 *            defines the {@link Scenarios.Scenario scenrio's} speed. The value
 *            is a delay in milliseconds between each loop.
 * @param generation
 *            holds the current generation the simulation has reach so it may be
 *            displayed via the {@link DataCenter.Storage panel}
 * @param cores
 *            is a variable used when
 *            {@link Scenarios.Scenario#splitTask(int doing) generating the
 *            cells} at the start. It dictates how many cores should be used for
 *            the operation. 0 runs the operation on the main thread. This is
 *            best for laptops due to intel boost tech.
 * @param mutation
 *            Does what it says, controls the muation chance of cells.
 * @param distance
 *            The area around in cell in which other cells are considered
 *            neighboors.
 * @param taurus
 *            >> True or False, do you want the simulation to use taurus logic?
 *            <p>
 *            <b>Prisonner Dilemma Logic</b>
 * @param T
 *            Highest payoff, attributed to a defector when encountering a
 *            cooperator.
 * @param R
 *            Second payoff, attributed to a cooperator when encountering a
 *            cooperator.
 * @param P
 *            third payoff, attributed to a defector when encountering another
 *            defector.
 * @param S
 *            Worst payoff, attributed to a cooperator when encountering a
 *            defector.
 */
public class Bridge implements Serializable
{
	// General variables
	private ArrayList<Cell> cells;
	private ArrayList<String> settings;
	private String path ="C:\\Users\\hoijf\\Desktop\\sim_saves";
	// Scenario variables
	private int cellCount;
	private boolean running;
	private int generation;
	private int totalGen;
	private int cores;
	private int size = 1;
	private int saveEvery;
	// Cell logic
	private int itPerGen;
	private int mutation;
	private double distance;
	private double T = 1.9;
	private double R = 1;
	private double P = 0;
	private double S = 0;
	private boolean taurus = false;
	private int memorySize = 1;
	private int nodes = 2;

	
	//GENERAL
	public void setSettings(ArrayList<String> settings)
	{
		this.settings = settings;
	}
	
	public ArrayList<String> getSettings()
	{
		return settings;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public String getPath()
	{
		return path;
	}
	// Simulation logic
	public void setCellCount(int cellCount)
	{
		this.cellCount = cellCount;
	}
	
	public int getCellCount()
	{
		return cellCount;
	}
	
	public boolean running()
	{
		return running;
	}

	public void setRunning(boolean running)
	{
		this.running = running;
	}
	
	public void setTotalGen(int totalGen)
	{
		this.totalGen = totalGen;
	}
	
	public int getTotalGen()
	{
		return totalGen;
	}
	
	public void setSaveEvery(int saveEvery)
	{
		this.saveEvery = saveEvery;
	}
	
	public int getSaveEvery()
	{
		return saveEvery;
	}

	// Getters and Setters
	public ArrayList<Cell> getCells()
	{
		return cells;
	}

	public void setCells(ArrayList<Cell> cells)
	{
		this.cells = cells;
		this.size = cells.size();
	}

	// Cell logic
	public void setNodes(int nodes)
	{
		this.nodes = nodes;
	}
	public int getNodes()
	{
		return nodes;
	}
	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setItPerGen(int itPerGen)
	{
		this.itPerGen = itPerGen;
	}

	public int getItPerGen()
	{
		return itPerGen;
	}

	public void setMutation(int mutation)
	{
		this.mutation = mutation;
	}

	public int getMutation()
	{
		return mutation;
	}

	public void setT(double T)
	{
		this.T = T;
	}

	public void setR(double R)
	{
		this.R = R;
	}

	public void setP(double P)
	{
		this.P = P;
	}

	public void setS(double S)
	{
		this.S = S;
	}

	public double getT()
	{
		return T;
	}

	public double getR()
	{
		return R;
	}

	public double getP()
	{
		return P;
	}

	public double getS()
	{
		return S;
	}

	public int getGen()
	{
		return generation;
	}

	public void setGen(int gen)
	{
		this.generation = gen;
	}
	
	public void addGen()
	{
		generation++;
	}

	public boolean isTaurus()
	{
		return this.taurus;
	}

	public void setTaurus(boolean taurus)
	{
		this.taurus = taurus;
	}

	public int getCores()
	{
		return cores;
	}

	public void setCores(int cores)
	{
		this.cores = cores;
	}

	public void setMemory(int memory)
	{
		this.memorySize = memory;
	}

	public int getMemory()
	{
		return this.memorySize;
	}

	public int getSize()
	{
		return this.size;
	}
}
