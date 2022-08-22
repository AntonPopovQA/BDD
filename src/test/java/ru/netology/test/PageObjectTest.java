package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV1;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.getCards;

public class PageObjectTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldBalanceCards() {
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var DashBoardPage = new DashboardPage();
        var balanceFirstCard = DashBoardPage.getCardBalance(getCards().getIdFirstCard());
        var balanceSecondCard = DashBoardPage.getCardBalance(getCards().getIdSecondCard());
    }

    @Test
    void shouldTransferOnTheSecondCard() {
        int amount = 1_000;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var DashBoardPage = new DashboardPage();
        var balanceFirstCard = DashBoardPage.getCardBalance(getCards().getIdFirstCard());
        var balanceSecondCard = DashBoardPage.getCardBalance(getCards().getIdSecondCard());
        DashBoardPage.firstCard().transferCards(String.valueOf(amount), DataHelper.getCards().getNumberSecondCard());
        var actualBalanceFirstCard = DashBoardPage.getCardBalance(getCards().getIdFirstCard());
        var actualBalanceSecondCard = DashBoardPage.getCardBalance(getCards().getIdSecondCard());
        var expectedBalanceFirstCard = balanceFirstCard + amount;
        var expectedBalanceSecondCard = balanceSecondCard - amount;
        Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferOnTheFirstCard() {
        int amount = 2_000;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var DashBoardPage = new DashboardPage();
        var balanceFirstCard = DashBoardPage.getCardBalance(getCards().getIdFirstCard());
        var balanceSecondCard = DashBoardPage.getCardBalance(getCards().getIdSecondCard());
        DashBoardPage.secondCard().transferCards(String.valueOf(amount), DataHelper.getCards().getNumberFirstCard());
        var actualBalanceFirstCard = DashBoardPage.getCardBalance(getCards().getIdFirstCard());
        var actualBalanceSecondCard = DashBoardPage.getCardBalance(getCards().getIdSecondCard());
        var expectedBalanceFirstCard = balanceFirstCard - amount;
        var expectedBalanceSecondCard = balanceSecondCard + amount;
        Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferToTheSameCard() {
        int amount = 1_000;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var DashBoardPage = new DashboardPage();
        var balanceFirstCard = DashBoardPage.getCardBalance(getCards().getIdFirstCard());
        DashBoardPage.firstCard().transferCards(String.valueOf(amount), DataHelper.getCards().getNumberFirstCard());
        new TransferPage().error();
    }

    @Test
    void shouldTransferMoreBalance() {
        int amount = 15_000;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var DashBoardPage = new DashboardPage();
        var balanceSecondCard = DashBoardPage.getCardBalance(getCards().getIdSecondCard());
        DashBoardPage.secondCard().transferCards(String.valueOf(amount), DataHelper.getCards().getNumberFirstCard());
        new TransferPage().error();
    }


}

