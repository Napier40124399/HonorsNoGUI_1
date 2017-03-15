package Cells;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import DataCenter.Bridge;
import NetworkFinal.Network;

/**
 * <h1>Cellular</h1> Super class to all cells. Makes use of protected variables
 * so edit and use with great care!! <br>
 * Each time a cell is created, {@link Cells.Cell_Hard hardcoded},
 * {@link Cells.Cell_TitTat titFortat} or otherwise, this will be accessible and
 * replace much of the hassle of repeating code. Each cell has its own instance
 * of the super class.
 * 
 * @param bridge
 *            Extremely important variable. This allows the cells to communicate
 *            to each other, as well as to the outside. Without correct use of
 *            this, the simulation will not work.
 * @param neighboors
 *            An {@link ArrayList} of the neighbooring cells. This is defined by
 *            running {@link Cells.Cellular#setNeighboors() setNeighboors}, a
 *            method inside this class.
 * @param coords
 *            This cell's position in the visual representation. This is also
 *            used for certain logic elements.
 * @see Color
 * @see Point2D
 * @see Runnable
 */
public class Cellular
{
	// Changing values
	// Positions
	protected int x;
	protected int y;
	protected Point2D coords;
	// Prisoner Dilemma vars
	protected double fitness;
	protected boolean R;
	protected boolean nextGenR;
	protected ArrayList<Cell> daddys;
	// Visuals
	protected Color c;
	// Stable values
	protected Bridge bridge;
	protected ArrayList<Cell> neighboors = new ArrayList<Cell>();

	/**
	 * @see {@link Cells.Cell#Cell(boolean, int, int, Bridge) Info Here}
	 */
	public void Cell(boolean R, int x, int y, Bridge bridge)
	{
		this.R = R;
		coords = new Point2D.Double(x, y);
		this.x = x;
		this.y = y;
		this.bridge = bridge;
		if (R)
		{
			c = Color.red;
		} else
		{
			c = Color.blue;
		}
	}

	public void mutationLogic()
	{
		int mRate = bridge.getMutation();

	}

	/**
	 * <h1>Setting Neighboors</h1> Sets current cell's neighboors. This is best
	 * done in the preperation phase of the simulation since it uses lots of
	 * ressources. This permits for much faster manipulation of data rather than
	 * having to find the neighbooring cells every time. <br>
	 * This algorithm is already relatively efficient. Change at your own risk.
	 * <p>
	 * <b>Methods</b>
	 * <ul>
	 * <li>{@link Cells.Cellular#notNormal() notNormal()}</li>
	 * <li>{@link Cells.Cellular#isNormal() isNormal()}</li>
	 * </ul>
	 * <p>
	 * 
	 * @param distance
	 *            is set by the user. This allows for a radius of interraction
	 *            to be created.
	 * @param neighboors
	 *            this is an arraylist containing references to cells which are
	 *            within the radius visitable by the current cell.
	 * @param size
	 *            is the size of one side of the lattice. Calulated using the
	 *            square root of the size of the global cell array.
	 * @param startX
	 *            describes the starting point for i to loop on.
	 * @param endX
	 *            describes the end point for i to loop on.
	 * @param startY
	 *            describes the starting point for j to loop on.
	 * @param endY
	 *            describes the end point for j to loop on.
	 * @param bridge.isTaurus()
	 *            is an external variable set by the user. This controls whether
	 *            the lattice is handled as a taurus or not.
	 * @see Bridge
	 * @see Math
	 */
	public void setNeighboors()
	{
		neighboors.clear();

		ArrayList<Cell> temp = bridge.getCells();
		int distance = (int) bridge.getDistance();
		int size = (int) Math.sqrt(temp.size());

		int startX = (int) coords.getY() - distance;
		int endX = (int) coords.getY() + distance;

		int startY = ((int) coords.getX() - distance) * size;
		int endY = ((int) coords.getX() + distance) * size;

		if (bridge.isTaurus())
		{
			if (startX < 0 || endX >= size)
			{
				notNormal(startX, startY, endX, endY, size, temp, distance);
			}

			else if ((coords.getX() - distance) < 0 || (coords.getX() + distance) >= size)
			{
				notNormal(startX, startY, endX, endY, size, temp, distance);
			}

			else
			{
				isNormal(startX, startY, endX, endY, size, temp);
			}
		} else
		{
			if (startX < 0 || endX >= size)
			{
			} else if ((coords.getX() - distance) < 0 || (coords.getX() + distance) >= size)
			{
			} else
			{
				isNormal(startX, startY, endX, endY, size, temp);
			}
		}
	}

	/**
	 * <h1>Neighboor calculations for complex situations</h1> This method
	 * handles abnormal cellular positions. Situations where a cell is close to
	 * an edge or may not have a neighboor where one would expect. Additional
	 * validation is in place as well as a more sophisticated algorithm to
	 * handle taurus logic.
	 *
	 *
	 * @param startX
	 *            describes the starting point for i to loop on.
	 * @param endX
	 *            describes the end point for i to loop on.
	 * @param startY
	 *            describes the starting point for j to loop on.
	 * @param endY
	 *            describes the end point for j to loop on.
	 * @param size
	 *            is the size of one side of the lattice. Calulated using the
	 *            square root of the size of the global cell array.
	 * @param temp
	 *            is an {@link ArrayList} containing the temporary set of
	 *            neighboors.
	 * @param dist
	 *            is the distance from the {@link Cells.Cellular#setNeighboors()
	 *            previous method (setNeighboors())}
	 */
	private void notNormal(int startX, int startY, int endX, int endY, int size, ArrayList<Cell> temp, int dist)
	{
		for (int i = startX; i <= endX; i++)
		{
			for (int j = startY; j <= endY; j += size)
			{
				int switcheroo = 0;

				// WORKS, DON'T QUESTION IT
				if (i < 0)
				{
					switcheroo += 1;
				}
				if (i >= size)
				{
					switcheroo += 2;
				}
				if (j < 0)
				{
					switcheroo += 4;
				}
				if (j >= size * size)
				{
					switcheroo += 8;
				}

				// Supposed to be more efficient than if statements
				switch (switcheroo)
				{
				case 0:
					neighboors.add(temp.get(i + j));
					break;
				case 1:
					neighboors.add(temp.get(regulateI(i, size, false) + j));
					break;
				case 2:
					neighboors.add(temp.get(regulateI(i, size, true) + j));
					break;
				case 4:
					neighboors.add(temp.get((regulateJ(j, size, false)) + i));
					break;
				case 8:
					neighboors.add(temp.get((regulateJ(j, size, true)) + i));
					break;

				// Corners
				case 5:
					neighboors.add(temp.get(regulateI(i, size, false) + regulateJ(j, size, false)));
					break;
				case 6:
					neighboors.add(temp.get(regulateI(i, size, true) + regulateJ(j, size, false)));
					break;
				case 9:
					neighboors.add(temp.get(regulateI(i, size, false) + regulateJ(j, size, true)));
					break;
				case 10:
					neighboors.add(temp.get(regulateI(i, size, true) + regulateJ(j, size, true)));
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * <h1>Neighboor Calulations</h1> Handles almost all neighboor calculations.
	 * All cells which don't need to make use of taurus logic will access this
	 * method. For parameters
	 *
	 * @param startX
	 *            describes the starting point for i to loop on.
	 * @param endX
	 *            describes the end point for i to loop on.
	 * @param startY
	 *            describes the starting point for j to loop on.
	 * @param endY
	 *            describes the end point for j to loop on.
	 * @param size
	 *            is the size of one side of the lattice. Calulated using the
	 *            square root of the size of the global cell array.
	 * @param temp
	 *            is an {@link ArrayList} containing the temporary set of
	 *            neighboors.
	 * @param dist
	 *            is the distance from the {@link Cells.Cellular#setNeighboors()
	 *            previous method (setNeighboors())}
	 */
	private void isNormal(int startX, int startY, int endX, int endY, int size, ArrayList<Cell> temp)
	{
		for (int i = startX; i <= endX; i++)
		{
			for (int j = startY; j <= endY; j += size)
			{
				neighboors.add(temp.get(i + j));
			}
		}
	}

	/**
	 * <h1>RegulateI</h1> Corrects vertical positioning for taurus use.
	 *
	 * @param pos
	 *            is the value which needs correcting.
	 * @param size
	 *            is the same as {@link Cells.Cellular#setNeighboors()
	 *            setNeighboors'}.
	 * @param upper
	 *            defines whether pos exceeds the upper bound or is lower than
	 *            0.
	 * @return The corrected value; functional under taurus logic.
	 */
	private int regulateI(int pos, int size, boolean upper)
	{
		int over = 0;
		if (upper)
		{
			over = pos - size; // Should be positive
		} else
		{
			over = size + pos; // Should be negative
		}
		return over;
	}

	/**
	 * <h1>RegulateJ</h1> Corrects vertical positioning for taurus use.
	 *
	 * @param pos
	 *            is the value which needs correcting.
	 * @param size
	 *            is the same as {@link Cells.Cellular#setNeighboors()
	 *            setNeighboors'}.
	 * @param upper
	 *            defines whether pos exceeds the upper bound or is lower than
	 *            0.
	 * @return The corrected value; functional under taurus logic.
	 */
	private int regulateJ(int pos, int size, boolean upper)
	{
		int over = 0;

		if (upper)
		{
			over = pos - size * size; // Should be positive
		} else
		{
			over = pos + size * size; // Should be negative
		}
		return over;
	}
	
	public void manage(int task)
	{
		if (task == 0)
		{
			//setFitness();
		} else if (task == 1)
		{
			//setNewGeneration();
		} else if (task == 2)
		{
			//updateCell();
		} else
		{
			setNeighboors();
		}
	}

	// ======================================================================================================================================================
	// ======================================================================================================================================================
	// ======================================================================================================================================================
	// ======================================================================================================================================================
	// ======================================================================================================================================================
	// ======================================================================================================================================================
	// ======================================================================================================================================================

	// Getters and Setters
	public double getPower()
	{
		return fitness;
	}

	public int getX()
	{
		return (int) coords.getX();
	}

	public int getY()
	{
		return (int) coords.getY();
	}

	public void setColor(Color co)
	{
		c = co;
	}

	public Color getColor()
	{
		return c;
	}

	public boolean getR()
	{
		return R;
	}

	public boolean getR(Point2D coords)
	{
		return R;
	}
	
	public Float getOut(Point2D coords)
	{
		return new Float(0);
	}

	public Network getNET()
	{
		return null;
	}

	public Point2D getCoords()
	{
		return coords;
	}

	public ArrayList<Cell> getNeighboors()
	{
		return this.neighboors;
	}

	public ArrayList<ArrayList<Float>> getWeights()
	{
		return null;
	}
	
	public int getType()
	{
		return 99;
	}
	
	public int getFlag()
	{
		return 99;
	}
	
	public Network getNetwork()
	{
		return null;
	}
	
	public void resetMemory()
	{
		
	}
	
	public void handleMemory(Float decisionME, Float decisionOP)
	{
		
	}
}
