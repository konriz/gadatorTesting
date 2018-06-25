package gadator;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Selenide.$;
import static gadator.TestConstants.*;

/**
 * First test to be runned
 */
public class UserTestSelenide {

    @BeforeClass
    public static void openGadator()
    {
        Selenide.open(testURL);
    }

    public static void login(String username, String password)
    {
        $(By.partialLinkText("Login")).click();
        $("#name-login").setValue(username);
        $("#password-login").setValue(password);
        $("#submit-login").click();
    }

    public static void logout()
    {
        if ($(By.partialLinkText("Logout")).exists()) {
            $(By.partialLinkText("Logout")).click();
        }
    }

    @Test
    public void loginAsAdminAndLogout()
    {
        login(adminName, adminPass);

        $(By.xpath("//div[@id='identity-label']/p")).shouldHave(Condition.matchesText(adminName));
        $(By.partialLinkText("Logout")).should(exist);

        $(By.partialLinkText("Logout")).click();
        $(By.partialLinkText("Login")).should(exist);
    }

    public static void registerUser(String username, String email, String password)
    {
        $("#registration-button").click();
        $("#name").setValue(username);
        $("#email").setValue(email);
        $("#password").setValue(password);
        $("#confirmPassword").setValue(password);
        $(By.xpath("//form/input[@type='submit']")).click();
    }

    @Test
    public void createValidUserAndLogin()
    {
        registerUser(testUserName, testUserEmail, testUserPass);

        $(By.xpath("//body/h1")).shouldHave(matchesText(testUserName));

        login(testUserName, testUserPass);

        $(By.xpath("//div[@id='identity-label']/p")).shouldHave(Condition.matchesText(testUserName));
        $(By.partialLinkText("Logout")).should(exist);

        $(By.partialLinkText("Logout")).click();
        $(By.partialLinkText("Login")).should(exist);

    }

    // TODO this should be also tested!

    public static void deleteUser(String userName)
    {
        login(adminName, adminPass);
        $(By.partialLinkText("Users")).click();
        $(By.partialLinkText(userName)).click();
        $(By.partialLinkText("Delete")).click();
        $(By.tagName("form")).submit();
        logout();
    }

    @AfterClass
    public static void tearUp()
    {
        deleteUser(testUserName);
    }









}
