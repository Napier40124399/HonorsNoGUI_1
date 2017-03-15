package Cells;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import DataCenter.Bridge;
import NetworkFinal.Network;

public interface Cell
{
	/**
	 * <h1>Constructor; of sorts</h1> Replaces constructor so all cells can be
	 * constructed whenever is convenient and reconstructed.
	 * <p>
	 * 
	 * @param R
	 *            Defines whether the cell is R(defective) or !R(cooperative) >>
	 *            Think of it as R for red.
	 * @param x
	 *            Cell's position on X axis, also serves for neighboor
	 *            calculations.
	 * @param y
	 *            Cell's position on Y axis, also serves for neighboor
	 *            calculations.
	 * @param bridge
	 *            This method requires a bridge connection for data to flow
	 *            between threads!!
	 * @see Point2D
	 * @see Bridge
	 */
	void Cell(boolean R, int x, int y, Bridge bridge);

	/**
	 * <h1>SetNeighboors</h1> This method is not part of the loop although it
	 * can be called at times if a cell's neighboors change. Currently this
	 * cannot happen but with mutation taking a larger and larger role in
	 * simulation, the neural network operated cells could eventually
	 * incorporate a distance variable allowing them to extend or diminish their
	 * reach.
	 * 
	 * @see {@link Cells.Cell#setCell() Constructor. cell setter}
	 * @see {@link Cells.Cell#setNeighboors() Neighboor. calculations}
	 * @see {@link Cells.Cell#setFitness() 1. fitness logic}
	 * @see {@link Cells.Cell#setNewGeneration() 2. new generation logic}
	 * @see {@link Cells.Cell#updateCell() 3. update logic}
	 */
	void setNeighboors();

	/**
	 * <h1>MutationLogic</h1> This method is part of the loop.
	 * 
	 * @see {@link Cells.Cell#setCell() Constructor. cell setter}
	 * @see {@link Cells.Cell#setNeighboors() Neighboor. calculations}
	 * @see {@link Cells.Cell#setFitness() 1. fitness logic}
	 * @see {@link Cells.Cell#setNewGeneration() 2. new generation logic}
	 * @see {@link Cells.Cell#updateCell() 3. update logic}
	 */
	void mutationLogic();
// 1
	/**
	 * <h1>Set Fitness</h1> Unlike the method name, this is not really a setter.
	 * It instead triggers the calculations of a cell's fitness.
	 * 
	 * @see {@link Cells.Cell#setCell() Constructor. cell setter}
	 * @see {@link Cells.Cell#setNeighboors() Neighboor. calculations}
	 * @see {@link Cells.Cell#setFitness() 1. fitness logic}
	 * @see {@link Cells.Cell#setNewGeneration() 2. new generation logic}
	 * @see {@link Cells.Cell#updateCell() 3. update logic}
	 */
	void setFitness();
// 2
	/**
	 * <h1>Set New Generation</h1> Unlike the method name, this is not really a
	 * setter. It instead finds which cell will be the parent of the new cell.
	 * 
	 * @see {@link Cells.Cell#setCell() Constructor. cell setter}
	 * @see {@link Cells.Cell#setNeighboors() Neighboor. calculations}
	 * @see {@link Cells.Cell#setFitness() 1. fitness logic}
	 * @see {@link Cells.Cell#setNewGeneration() 2. new generation logic}
	 * @see {@link Cells.Cell#updateCell() 3. update logic}
	 */
	void setNewGeneration();
// 3
	/**
	 * <h1>UpdateCell</h1> This method is part of the loop, called at a
	 * consistent rate. <br>
	 * 3rd in call list, handles the transition from current to next generation.
	 * 
	 * @see {@link Cells.Cell#setCell() Constructor. cell setter}
	 * @see {@link Cells.Cell#setNeighboors() Neighboor. calculations}
	 * @see {@link Cells.Cell#setFitness() 1. fitness logic}
	 * @see {@link Cells.Cell#setNewGeneration() 2. new generation logic}
	 * @see {@link Cells.Cell#updateCell() 3. update logic}
	 */
	void updateCell();

	// getters and setters
	double getPower();
	
	int getType();
	
	int getFlag();

	int getX();

	int getY();

	void setColor(Color c);

	Color getColor();

	boolean getR();

	boolean getR(Point2D coords);
	
	Float getOut(Point2D coords);

	Network getNET();

	Point2D getCoords();

	ArrayList<Cell> getNeighboors();

	ArrayList<ArrayList<Float>> getWeights();
	
	Network getNetwork();

	/**
	 * <h1>Manage</h1> An obscure way of creating a detached logic.
	 * 
	 * @see {@link Cells.Cell#setCell() Constructor. cell setter}
	 * @see {@link Cells.Cell#setNeighboors() Neighboor. calculations}
	 * @see {@link Cells.Cell#setFitness() 1. fitness logic}
	 * @see {@link Cells.Cell#setNewGeneration() 2. new generation logic}
	 * @see {@link Cells.Cell#updateCell() 3. update logic}
	 */
	void manage(int task);
	void resetMemory();
	void handleMemory(Float decisionME, Float decisionOP);
}
