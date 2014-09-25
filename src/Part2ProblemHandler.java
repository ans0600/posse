import com.mower.Mower;
import com.mower.exception.FatalException;
import com.mower.lawn.Coordinate;
import com.mower.lawn.Lawn;


public class Part2ProblemHandler extends ProblemHandler {

	final String problemID="2";
	
	private Lawn lawn;
	
	private int numOfMowers;
	
	public boolean shouldProcess(String problemID) {
		return problemID.equals(this.problemID);
	}

	public void process(Object[] input) {
		
		if(this.readFile((String)input[1])&&lawn!=null&&numOfMowers!=0)
		{
			this.lawn.autoTaskMowers(numOfMowers);
			
			System.err.println(this.lawn.getMowers().size());
			
			for(Mower mower:this.lawn.getMowers())
			{
				System.out.println(mower.getInitPosition());
				System.out.println(mower.getCommand());
			}
			
		}else
		{
			System.err.println("Unable to proceed as input is not complete or there is 0 mowers");
		}
		

	}

	@Override
	protected boolean handleLine(String line) {
		
		try
		{
			String lineParts[]=line.split(" ");
			if(lineParts.length==3)
			{
				this.lawn=new Lawn(new Coordinate(Integer.parseInt(lineParts[0]), Integer.parseInt(lineParts[1])));
				this.numOfMowers=Integer.parseInt(lineParts[2]);
			}else
			{
				throw new FatalException("Expecting Coordinate and Mower Amount. Unable to parse content:"+line);
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}
	


}
