package gadator;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({UserTestSelenide.class, ConversationTestSelenide.class, MessagesTestSelenide.class})
@RunWith(Suite.class)
public class TestSuiteSelenide {
}
