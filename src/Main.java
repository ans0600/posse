import java.util.ArrayList;

import com.mower.Mower;
import com.mower.lawn.Lawn;


public class Main {

	public static void main(String[] args) {
		
		ArrayList<ProblemHandler> handlers=new ArrayList<ProblemHandler>();
		handlers.add(new Part1ProblemHandler());
		handlers.add(new Part2ProblemHandler());
		
		if(args.length!=3)
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
		System.out.println("Usage: [BinaryName] [PartNumber] [InputFile] [OutputFile]");
		System.out.println("Example: mower.jar 1 /part1_input /part1_output");
		System.out.println("Example: mower.jar 2 /part2_input /part2_output");
		
	}
	
}
