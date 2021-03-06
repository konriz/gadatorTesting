package gadator;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static gadator.TestConstants.*;


/**
 * Conversation Test must be all right to test messaging functionalities
 */
public class MessagesTestSelenide {

    private static String testConversationName;

    private static void registerUser()
    {
        UserTestSelenide.registerUser(testUserName, testUserEmail, testUserPass);
    }

    private static void loginUser()
    {
        UserTestSelenide.login(testUserName, testUserPass);
    }

    private static void loginAdmin()
    {
        UserTestSelenide.login(adminName, adminPass);
    }

    private static void logout()
    {
        UserTestSelenide.logout();
    }

    private static void deleteUser()
    {
        UserTestSelenide.deleteUser(testUserName);
    }

    @BeforeClass
    public static void createTestUserAndTestConversation()
    {
        Selenide.open(testURL);

        testConversationName = "Conversation from " + (new Date()).toString();

        registerUser();
        loginUser();

        $(By.partialLinkText("Conversations")).click();
        $(By.partialLinkText("Create new")).click();
        $("#name").setValue(testConversationName).pressEnter();

    }

    @Test
    public void addMessageToConversationAsTestUser()
    {
        String messageContent = "Message with content no. " + Math.random();


        $(By.partialLinkText("Conversations")).click();
        $(By.partialLinkText(testConversationName)).click();

        $(By.id("message")).setValue(messageContent).submit();

        $$(By.className("post")).shouldHaveSize(1);

        $$(By.className("post")).last()
                .shouldHave(Condition.matchText(testUserName))
                .shouldHave(Condition.matchText(messageContent));
    }

    @AfterClass
    public static void tearUp()
    {
        logout();
        loginAdmin();
        $(By.partialLinkText("Conversations")).click();
        $(By.partialLinkText(testConversationName)).click();
        $(By.partialLinkText("Delete conversation")).click();
        $(By.xpath("//form")).submit();

        logout();

        deleteUser();
    }
}
