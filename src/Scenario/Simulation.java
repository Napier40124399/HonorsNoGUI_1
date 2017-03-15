package Scenario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Cells.Cell;
import DataCenter.Bridge;
import DataCenter.SerializeData;

public class Simulation implements Runnable
{
	private int counter;
	private Bridge bridge;
	private ArrayList<Cell> cells;
	private SplitTask sp = new SplitTask();
	private ArrayList<ArrayList<String>> serializeAtEnd = new ArrayList<ArrayList<String>>();

	public Simulation(Bridge bridge)
	{
		this.bridge = bridge;
		cells = bridge.getCells();
	}

	@Override
	public void run()
	{
		counter = 0;
		for (Cell ce : cells)
		{
			ce.setNeighboors();
		}
		for (int i = 0; i < bridge.getTotalGen(); i++)
		{
			live();
			counter++;
			checkSave();
			System.out.println("Gen: "+bridge.getGen());
		}
		if (bridge.getTotalGen() == 0)
		{
			while (bridge.running())
			{
				live();
				counter++;
				checkSave();
				System.out.print("\rGen: "+bridge.getGen());
			}
		}
		serialize();
		System.out.println("******** Simulation finished ********");
	}

	private void live()
	{
		multiThread();
		bridge.addGen();
	}
	
	private void multiThread()
	{
		sp.splitCores(cells, bridge.getCores(), bridge, 1);
		sp.splitCores(cells, bridge.getCores(), bridge, 2);
		sp.splitCores(cells, bridge.getCores(), bridge, 4);
		sp.splitCores(cells, bridge.getCores(), bridge, 3);
	}
	
	private void basic()
	{
		for (Cell ce : cells)
		{
			ce.setFitness();
		}
		for (Cell ce : cells)
		{
			ce.setNewGeneration();
		}
		for (Cell ce : cells)
		{
			ce.updateCell();
		}
		for (Cell ce : cells)
		{
			ce.mutationLogic();
		}
	}
	
	private void checkSave()
	{
		if(counter == bridge.getSaveEvery())
		{
			
			ArrayList<String> loc = new ArrayList<String>();
		    for(Cell c : cells)
		    {
		    	loc.add(c.serialize());
		    }
		    serializeAtEnd.add(loc);


		    BufferedImage img = new BufferedImage(bridge.getCellCount(), bridge.getCellCount(), BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			for(Cell ce : cells)
			{
				g.setColor(ce.getC2());
				g.drawLine(ce.getX(), ce.getY(), ce.getX(), ce.getY());
			}
			try
			{
				File outputfile = new File(bridge.getPath()+"\\save_"+bridge.getGen()+".png");
				ImageIO.write(img, "png", outputfile);
			} catch (IOException e)
			{
			}
			
			
			
			counter = 0;
		}
	}
	
	private void serialize()
	{
		int iteration = 0;
		for(ArrayList<String> gen : serializeAtEnd)
		{
			try (ObjectOutputStream oos =
					new ObjectOutputStream(new FileOutputStream(bridge.getPath()+"\\"+iteration)))
			{
				oos.writeObject(gen);
				iteration++;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setCells()
	{
		cells = bridge.getCells();
	}

	public void setBridge(Bridge bridge)
	{
		this.bridge = bridge;
	}
}
