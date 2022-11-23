import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunTests {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);

		for (Failure failure : result.getFailures()) 
		{
			System.out.println(failure.toString());
		}
		
		if (result.wasSuccessful()) System.out.println("Tests passed with no failures.");
		else if (!result.wasSuccessful()) System.out.println("Tests executed with failures.");
	}
}