package Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Scenario.Scenario;

public class Launcher
{
	private Scanner scanner = new Scanner(System.in);
	private ArrayList<String> settings = new ArrayList<String>();
	private FileChooser fc;
	private Scenario sim;
	private String p;
	
	public void start()
	{
		System.out.println("Select output path:");
		File f = new FileChooser().getFolder("C:\\");
		p = f.getPath();
		System.out.println("Evolutionary Biology Simulation Software");
		System.out.println("========================================");
		System.out.println("1) Input Settings Manualy");
		System.out.println("2) Load Setting From File");

		int i = Integer.parseInt(scanner.nextLine());

		switch (i)
		{
		case 1:
			ManualInput();
			break;
		case 2:
			LoadFile();
			confirm();
			break;
		case 3:
			fast();
			break;
		default:
			System.out.println("ERROR - PLEASE RESTART PROGRAM!");
		}

		// Restart
		clearScreen();
		start();
	}

	private void ManualInput()
	{
		settings.clear();
		System.out.println("********** Manual Input **********");
		System.out.println("Cell type:\n1:HardCoded  |  2:TFT  |  3:NeuralNet");
		settings.add(scanner.nextLine());

		System.out.println("Asynchronous?\n1:Asynchronous  |  0:Synchronous");
		settings.add(scanner.nextLine());
		System.out.println("How many cells? (This value will be squared)");
		settings.add(scanner.nextLine());
		System.out.println("How many generations? (0 = no limit)");
		settings.add(scanner.nextLine());
		System.out.println("How many iterations per generation?\n(1 is minimum value)");
		settings.add(scanner.nextLine());
		System.out.println("Save image every X generations.\nX = ");
		settings.add(scanner.nextLine());
		System.out.println("Distance\n(range over which other cells are neighboors)");
		settings.add(scanner.nextLine());
		System.out.println("Taurus?\n1:yes  |  2:no");
		settings.add(scanner.nextLine());
		System.out.println("How many threads?");
		settings.add(scanner.nextLine());
		System.out.println("How many nodes?");
		settings.add(scanner.nextLine());
		System.out.println("Mutation on connection weights\n(will be multiplied by 0.001):");
		settings.add(scanner.nextLine());
		System.out.println("1) Run\n2) Save\n3) Save & Run");
		String t = scanner.nextLine();
		if (t.equals("1"))
		{
			confirm();
		} else if (t.equals("2"))
		{
			SaveFile();
		} else
		{
			SaveFile();
			confirm();
		}
	}

	private void await()
	{
		readSettings();
		System.out.println("******** SIMULATION RUNNING ********");
		System.out.println("Input anything to end simulation.");
		scanner.nextLine();
		sim.stop();
	}

	private void SaveFile()
	{
		clearScreen();
		System.out.println("********** Saving File **********");
		System.out.println("1: Open file chooser  |  2: Enter save directory manualy");
		String s = scanner.nextLine();

		if (s.equals("1"))
		{
			File f = new FileChooser().getFolder(p);
			s = f.getPath();
			System.out.println("selected" + s);
		} else
		{
			System.out.println("Enter save directory manualy using \\ delimiters (none at the end):");
			System.out.println("ex: C:\\Users");
			s = scanner.nextLine();
		}

		System.out.println("Enter save file name:");
		String name = scanner.nextLine();

		File file = new File(s, "\\" + name + ".txt");
		try
		{
			BufferedWriter r = new BufferedWriter(new FileWriter(file));
			for (String ss : settings)
			{
				r.write(ss);
				r.newLine();
			}
			r.close();
			System.out.println("Saved.");
		} catch (IOException e1)
		{
			System.out.println("Save failed.");
		}
	}

	private void LoadFile()
	{
		clearScreen();
		System.out.println("********** Loading File **********");
		System.out.println("1: Open file chooser  |  2: Enter path manualy");
		String s = scanner.nextLine();
		if (s.equals("1"))
		{
			File f = new FileChooser().getFile(p);
			s = f.getPath();
		} else
		{
			System.out.println("Enter path manualy using \\ delimiters:");
			System.out.println("ex: C:\\Users\\details.txt");
			s = scanner.nextLine();
		}

		try
		{
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(s);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			settings.clear();
			while ((line = bufferedReader.readLine()) != null)
			{
				settings.add(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e)
		{
		} catch (IOException ex)
		{
		}
	}

	private void readSettings()
	{
		clearScreen();
		System.out.println("********** READING SETTINGS **********");

		if (settings.get(0).equals("1"))
		{
			System.out.println("0. Cell type HardCoded.");
		} else if (settings.get(0).equals("2"))
		{
			System.out.println("0. Cell type TFT.");
		} else if (settings.get(0).equals("3"))
		{
			System.out.println("0. Cell type NeuralNet.");
		} else
		{
			System.out.println("0. error *** " + settings.get(0));
		}

		if (settings.get(1).equals("1"))
		{
			System.out.println("1. Asynchronous");
		} else if (settings.get(1).equals("0"))
		{
			System.out.println("1. Synchronous");
		} else
		{
			System.out.println("1. error *** " + settings.get(1));
		}

		System.out.println("2. Cells: " + settings.get(2));

		if (settings.get(3).equals("0"))
		{
			System.out.println("3. No generation limit.");
		} else
		{
			System.out.println("3. Generations: " + settings.get(3));
		}

		System.out.println("4. Iterations per generation: " + settings.get(4));

		System.out.println("5. Save image every " + settings.get(5) + " generations.");

		System.out.println("6. Distance: " + settings.get(6));

		if (settings.get(7).equals("1"))
		{
			System.out.println("7. Taurus active.");
		} else if (settings.get(7).equals("0"))
		{
			System.out.println("7. Taurus not active!");
		} else
		{
			System.out.println("7. error *** " + settings.get(7));
		}

		System.out.println("8. Threads: "+settings.get(8));
		System.out.println("9. Nodes: "+settings.get(9));
		System.out.println("10. Mutation: "+settings.get(10));
		System.out.println("********** END OF READING **********");
	}

	private void clearScreen()
	{
		for (int i = 0; i < 10; i++)
		{
			System.out.println("\n");
		}
	}
	
	private void fast()
	{
		try
		{
			String s = p+"\\save1.txt";
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(s);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			settings.clear();
			while ((line = bufferedReader.readLine()) != null)
			{
				settings.add(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e)
		{
		} catch (IOException ex)
		{
		}
		sim = new Scenario(settings);
		await();
	}

	private void confirm()
	{
		readSettings();
		System.out.println("Correct [y/n/e]?");
		String s = scanner.nextLine();
		settings.add(p);
		if (s.equals("y") || s.equals("Y"))
		{
			sim = new Scenario(settings);
		} else if(s.equals("e") || s.equals("E"))
		{
			edit();
		}
		await();
	}
	
	private void edit()
	{
		System.out.println("Which setting would you like to edit?");
		System.out.println("Input number associated with setting.");
		String s = scanner.nextLine();
		System.out.println("Enter new value:");
		settings.set(Integer.parseInt(s), scanner.nextLine());
		SaveFile();
		confirm();
	}
}
