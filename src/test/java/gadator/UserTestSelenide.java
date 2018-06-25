package gadator;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static gadator.TestConstants.*;

/**
 * First test to be runned
 */
public class UserTestSelenide {

    @BeforeClass
    public static void openGadator()
    {

        Selenide.open("https://gadator-testing.herokuapp.com/");
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

        $(By.xpath("//div[@id='identity-label']/p")).shouldHave(Condition.matchesText("admin"));
        $(By.partialLinkText("Logout")).should(exist);

        $(By.partialLinkText("Logout")).click();
        $(By.partialLinkText("Login")).should(exist);
    }


    @Test
    public void grantedUserExists_loginAsUserAndLogout()
    {
        login(testUserName, testUserPass);

        $(By.xpath("//div[@id='identity-label']/p")).shouldHave(Condition.matchesText(testUserName));
        $(By.partialLinkText("Logout")).should(exist);

        $(By.partialLinkText("Logout")).click();
        $(By.partialLinkText("Login")).should(exist);
    }



//        forbidden
//    @Test
//    public void createUser()
//    {
//        final String username = "tester";
//        final String email = "tester@user.com";
//        final String password = "examplePassword";
//
//        register(username, email, password);
//
//        $(By.xpath("//body/h1")).shouldHave(textCaseSensitive("Welcome " + username));
//    }
//
//    private void register(String username, String email, String password)
//    {
//        $("#registration-button").click();
//        $("#name").setValue(username);
//        $("#email").setValue(email);
//        $("#password").setValue(password);
//        $("#confirmPassword").setValue(password);
//        $(By.xpath("//form/input[@type='submit']")).click();
//    }


}
