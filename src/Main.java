import java.util.ArrayList;

import com.mower.util.Util;



public class Main {

	public static void main(String[] args) {
		
		Util.setLogLevel(Util.LOG_STD);
		ArrayList<ProblemHandler> handlers=new ArrayList<ProblemHandler>();
		handlers.add(new Part1ProblemHandler());
		handlers.add(new Part2ProblemHandler());
		
		if(args.length!=2)
		{
			printUsage();
		}else
		{
			boolean taskHandled=false;
			for(ProblemHandler handler:handlers)
			{
				if(handler.shouldProcess(args[0]))
				{
					handler.process(args);
					taskHandled=true;
					break;
				}
			}
			if(!taskHandled)
			{
				System.out.println("PartNumber is not recognised.");
				printUsage();
			}
			
		}
			

	}
	
	
	public static void printUsage()
	{
		System.out.println("Usage: [BinaryName] [PartNumber] [InputFile] ");
		System.out.println("Example: mower.jar 1 /part1_input");
		System.out.println("Example: mower.jar 2 /part2_input");
		
	}
	
	
}
