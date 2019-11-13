package testLink;

import org.springframework.stereotype.Component;
import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;

@Component
public class TestLink {
    public static final String PROJECT_NAME = "INGDevSchool";
    public static final String TEST_PLAN_NAME = "INGDS12-TestPlan";
    public static final String TEST_CASE_NAME = "Test Case 1";
    public static final String BUILD_NAME = "INGDS12-BUILD";

    private String key = "9244e666a4b2f242cae5d42b32ef7e1c";
    private String url = "http://127.0.0.1:81/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
    private TestLinkAPIClient testLinkAPI = new TestLinkAPIClient(key, url);

    public void updateTest(String testCaseName, String status) {
        try {
            testLinkAPI.reportTestCaseResult(PROJECT_NAME, TEST_PLAN_NAME, testCaseName, BUILD_NAME, "dfg", status);
        } catch (TestLinkAPIException e) {
            throw new RuntimeException("Error occurred while updating testlink!", e);
        }
    }

    public void addTestCaseToPlan(String testcase) {
        try {
            testLinkAPI.addTestCaseToTestPlan(PROJECT_NAME, TEST_PLAN_NAME, testcase);
        } catch (TestLinkAPIException e) {
            throw new RuntimeException("Error occurred while adding testcase!", e);
        }
    }

    public void ping()  {
        try {
            System.out.println("ping incoming...");
            System.out.println(testLinkAPI.ping());
        } catch ( TestLinkAPIException e) {
            throw new RuntimeException("Error occurred while updating testlink!", e);
        }
    }
}
