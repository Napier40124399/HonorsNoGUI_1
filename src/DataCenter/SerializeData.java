package DataCenter;

import java.io.Serializable;
import java.util.ArrayList;

import Cells.Cell;

public class SerializeData implements Serializable
{
	private ArrayList<Cell> cells;
	
	public SerializeData(ArrayList<Cell> cells)
	{
		this.cells = cells;
	}
	
	public ArrayList<Cell> getCells()
	{
		return cells;
	}
}