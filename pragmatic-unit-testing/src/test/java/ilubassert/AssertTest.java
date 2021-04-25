package ilubassert;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ilubassert.PointMatcher.isNear;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AssertTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Account account;

    @BeforeClass
    public static void initializeSomethingReallyExpensive() {
        // ...
    }

    @AfterClass
    public static void cleanUpSomethingReallyExpensive() {
        // ...
    }

    @Before
    public void createAccount() {
        account = new Account("my big fat acct");
    }

    @After
    public void closeConnections() {
        // ...
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        //        assertTrue(account.getBalance() > initialBalance);
        //        hamcrest 단언이 실패할 경우 오류 메시지에서 더 많은 정보를 볼 수 있다.
        //        assertThat(account.getBalance() > initialBalance, Is.is
        //        (false));
        //        java.lang.AssertionError:
        //        Expected: is <false>
        //                but: was <true>
        //                Expected :is <false>
        //                Actual   :<true>
        assertThat(account.getBalance() > initialBalance, is(true));
        //        assertThat(account.getBalance(), IsEqual.equalTo(99));
        //        java.lang.AssertionError:
        //        Expected: <99>
        //                but: was <100>
        //                Expected :<99>
        //                Actual   :<100>
        assertThat(account.getBalance(), equalTo(100));
        assertThat(account.getName(), not(equalTo("plunderings")));
        assertThat(account.getName(), is(not(equalTo("plunderings"))));
        assertThat(account.getName(), not(nullValue()));
    }

    @Test
    public void testWithWorthlessAssertionComment() {
        account.deposit(50);
        assertThat("account balance is 100", account.getBalance(), equalTo(50));
    }

    @Test(expected = InsufficientFundsException.class)
    public void throwsWhenWithdrawingTooMuch() {
        account.withdraw(100);
    }

    @Test
    public void throwsWhenWithdrawingTooMuchTry() {
        try {
            account.withdraw(100);
            fail();
        } catch (InsufficientFundsException expected) {
            assertThat(expected.getMessage(), equalTo("balance only 0"));
        }
    }

    @Test
    public void exceptionRule() {
        thrown.expect(InsufficientFundsException.class);
        // thrown.expectMessage("cbalance only 0");
        thrown.expectMessage("balance only 0");
        account.withdraw(100);
    }

    @Test
    @Ignore
    public void readsFromTestFile() throws IOException {
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
        // ...
    }

    @Test
    public void testSameInstance() {
        Account a = new Account("a");
        Account aPrime = new Account("a");
        assertThat(a, not(sameInstance(aPrime)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void items() {
        List<String> names = new ArrayList<>();
        names.add("Moe");
        names.add("Larry");
        names.add("Curly");

        assertThat(names, hasItem("Curly"));
        assertThat(names, hasItems("Curly", "Moe"));
        assertThat(names, hasItem(endsWith("y")));
        assertThat(names, hasItems(endsWith("y"), startsWith("C"))); //warning!
        assertThat(names, not(everyItem(endsWith("y"))));
    }

    @Test
    @ExpectToFail
    @Ignore
    public void location() {
        Point point = new Point(4, 5);

        // WTF why do JUnit matches not include closeTo
        // assertThat(point.x, closeTo(3.6, 0.2));
        // assertThat(point.y, closeTo(5.1, 0.2));

        assertThat(point, isNear(3.6, 5.1));
    }

    class InsufficientFundsException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    class Account {
        int balance;
        String name;

        Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }
    }

    class Customer {
        List<Account> accounts = new ArrayList<>();

        void add(Account account) {
            accounts.add(account);
        }

        Iterator<Account> getAccounts() {
            return accounts.iterator();
        }
    }
}
