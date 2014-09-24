import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.mower.StandardMower;
import com.mower.exception.FatalException;
import com.mower.lawn.Coordinate;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;


public abstract class ProblemHandler {

	
	abstract public boolean shouldProcess(String problemID);
	
	abstract void process(Object[] input);
	
	abstract boolean handleLine(String line);
	
	
	protected boolean readFile(String filePath)
	{
		BufferedReader br=null;
		boolean success=false;
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = br.readLine()) != null) {
				
				if(!this.handleLine(line))break;
			}
			
			br.close();
			success= true;
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			success= false;
		}finally
		{
			try {
				if(br!=null)br.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return success;
		
	}
		
}
