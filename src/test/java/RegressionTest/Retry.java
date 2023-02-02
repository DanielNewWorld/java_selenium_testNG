package RegressionTest;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    int count = 0;
    int maxTry = 0;

    public Retry(){
    }

    public boolean retry(ITestResult result) {
        if (this.count < this.maxTry){
            ++this.count;
            return true;
        }else {
            return false;
        }
    }
}
