package gadator;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static gadator.TestConstants.*;
import static gadator.UserTestSelenide.login;
import static gadator.UserTestSelenide.logout;

/**
 * Tests to be runned after user tests
 */
public class ConversationTestSelenide {

    private static String testConversationName ;

    @BeforeClass
    public static void openGadator()
    {
        Selenide.open("https://gadator-testing.herokuapp.com/");
    }

    @Before
    public void setUp()
    {
        testConversationName = "Conversation from " + (new Date()).toString();
    }

    @Test
    public void createConversationAsTestUser()
    {
        login(testUserName, testUserPass);

        $(By.partialLinkText("Conversations")).click();
        $(By.partialLinkText("Create new")).click();
        $("#name").setValue(testConversationName).pressEnter();

        $(By.xpath("//body/h1")).shouldHave(Condition.matchesText(testConversationName));

        $(By.partialLinkText("Conversations")).click();
        $(By.partialLinkText(testConversationName)).should(Condition.exist);

        logout();
    }

    @Test
    public void deleteConversationAsAdmin()
    {
        createConversationAsTestUser();

        login(adminName, adminPass);

        $(By.partialLinkText("Conversations")).click();
        $(By.partialLinkText(testConversationName)).click();
        $(By.partialLinkText("Delete conversation")).click();
        $(By.xpath("//form")).submit();
        $(By.partialLinkText(testConversationName)).shouldNot(Condition.exist);

        logout();
    }

}
