package DataCenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class HandleSerialization
{
	public ArrayList<SerializeData> deSerializeData(String path)
	{
		System.out.println("ok");
		ArrayList<SerializeData> SDs = new ArrayList<SerializeData>();
		
		File folder = new File(path);
		File[] files = folder.listFiles();
		
		for(File f : files)
		{
			System.out.println("doing");
			if(f.getName().endsWith(".ser"))
			{
				SerializeData sd = null;
				try
				{
					FileInputStream fis = new FileInputStream(f.getPath());
					ObjectInputStream ois = new ObjectInputStream(fis);
					sd = (SerializeData) ois.readObject();
					SDs.add(sd);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return SDs;
	}
}
