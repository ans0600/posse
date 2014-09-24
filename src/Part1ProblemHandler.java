import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.mower.Mower;
import com.mower.StandardMower;
import com.mower.exception.CollisionException;
import com.mower.exception.FatalException;
import com.mower.lawn.Coordinate;
import com.mower.lawn.Lawn;
import com.mower.lawn.MowerCoordinate;


public class Part1ProblemHandler extends ProblemHandler {

	final String problemID="1";
	
	private Lawn lawn;
	
	private ArrayList<Mower> mowers;
	
	private Mower currentMower;
	
	private int lineCount;
	
	
	@Override
	public boolean shouldProcess(String problemID) {
		return problemID.equals(this.problemID);
	}

	@Override
	public void process(Object[] input) {
		
		this.mowers=new ArrayList<Mower>();
		
		if(this.readFile((String)input[1])&&lawn!=null&&mowers!=null&&mowers.size()>0)
		{
			for(Mower m:mowers)
			{
				lawn.addMower(m);
			}
			
			lawn.startMowers();
			for(Mower m:lawn.getMowers())
			{
				System.out.print(m.getCurrentLocation());
				if(!lawn.isInside(m.getCurrentLocation()))System.out.println(" [Out of bound]");
				else System.out.println("");
				
			}
			
			for(CollisionException ex:lawn.getCollisions())
			{
				System.out.println(ex);
			}
			
		}else
		{
			System.err.println("Unable to proceed as input is not complete.");
		}

	}
	
//	private boolean parseInput(String filePath)
//	{
//		int lineCount=0;
//		BufferedReader br = null;
//		boolean success=false;
//		try
//		{
//			br = new BufferedReader(new FileReader(filePath));
//			String line;
//			while ((line = br.readLine()) != null) {
//			//	System.err.println(line);
//			   if(lineCount==0)
//			   {
//				   String[] lineParts=line.split(" ");
//				   if(lineParts.length==2)
//				   {
//					   this.lawn=new Lawn(new Coordinate(Integer.parseInt(lineParts[0]),Integer.parseInt(lineParts[1])));
//				   }
//				   else
//				   {
//					   throw new FatalException("Expecting Boundry Coordinate. Invalid input at line: "+lineCount+" "+line);
//				   }
//			   }else
//			   {
//				   if(lineCount%2==0)
//				   {
//					   //command line
//					   if(this.currentMower!=null)
//					   {
//						   this.currentMower.setCommandStr(line);
//						   this.mowers.add(this.currentMower);
//						   this.currentMower=null;
//					   }
//				   }else
//				   {
//					   String[] lineParts=line.split(" ");
//					   if(lineParts.length==3)
//					   {
//						   this.currentMower=new StandardMower(new MowerCoordinate(Integer.parseInt(lineParts[0]), Integer.parseInt(lineParts[1]), Coordinate.getFacingByName(lineParts[2])));
//						   this.currentMower.setId(this.mowers.size());
//					   }else
//					   {
//						   throw new FatalException("Expecting Mower Coordinate. Invalid input at line: "+lineCount+" "+line);
//					   }
//					   
//				   }
//			   }
//			   
//			   lineCount++;
//			}
//			br.close();
//			success= true;
//		}catch(Exception ex)
//		{
//			System.out.println(ex.getMessage());
//			success= false;
//		}finally
//		{
//			try {
//				if(br!=null)br.close();
//			} catch (IOException e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		
//		return success;
//	}

	@Override
	protected boolean handleLine(String line) {
		
		try{
			 if(lineCount==0)
			   {
				   String[] lineParts=line.split(" ");
				   if(lineParts.length==2)
				   {
					   this.lawn=new Lawn(new Coordinate(Integer.parseInt(lineParts[0]),Integer.parseInt(lineParts[1])));
				   }
				   else
				   {
					   throw new FatalException("Expecting Boundry Coordinate. Invalid input at line: "+lineCount+" Content:"+line);
				   }
			   }else
			   {
				   if(lineCount%2==0)
				   {
					   //command line
					   if(this.currentMower!=null)
					   {
						   this.currentMower.setCommandStr(line);
						   this.mowers.add(this.currentMower);
						   this.currentMower=null;
					   }
				   }else
				   {
					   String[] lineParts=line.split(" ");
					   if(lineParts.length==3)
					   {
						   this.currentMower=new StandardMower(new MowerCoordinate(Integer.parseInt(lineParts[0]), Integer.parseInt(lineParts[1]), Coordinate.getFacingByName(lineParts[2])));
						   this.currentMower.setId(this.mowers.size());
					   }else
					   {
						   throw new FatalException("Expecting Mower Coordinate. Invalid input at line:"+lineCount+" Content:"+line);
					   }
					   
				   }
			   }
			   
			   lineCount++;
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		
		return true;
	}

}
