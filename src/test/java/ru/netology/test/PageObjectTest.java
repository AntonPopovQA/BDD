package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV1;


import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

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
        var DashBoardPage = verificationPage.validVerify(verificationCode);
        var balanceFirstCard = DashBoardPage.getCardBalance(getFirtsCard().getIdFirstCard());
        var balanceSecondCard = DashBoardPage.getCardBalance(getSecondCard().getIdSecondCard());
    }

    @Test
    void shouldTransferOnTheSecondCard() {
        int amount = 1_000;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var DashBoardPage = verificationPage.validVerify(verificationCode);
        var balanceFirstCard = DashBoardPage.getCardBalance(getFirtsCard().getIdFirstCard());
        var balanceSecondCard = DashBoardPage.getCardBalance(getSecondCard().getIdSecondCard());
        DashBoardPage.cards(getFirtsCard().getIdFirstCard()).transferCards(String.valueOf(amount), DataHelper.getSecondCard().getNumberSecondCard());
        var actualBalanceFirstCard = DashBoardPage.getCardBalance(getFirtsCard().getIdFirstCard());
        var actualBalanceSecondCard = DashBoardPage.getCardBalance(getSecondCard().getIdSecondCard());
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
        var DashBoardPage = verificationPage.validVerify(verificationCode);
        var balanceFirstCard = DashBoardPage.getCardBalance(getFirtsCard().getIdFirstCard());
        var balanceSecondCard = DashBoardPage.getCardBalance(getSecondCard().getIdSecondCard());
        DashBoardPage.cards(getSecondCard().getIdSecondCard()).transferCards(String.valueOf(amount), DataHelper.getFirtsCard().getNumberFirstCard());
        var actualBalanceFirstCard = DashBoardPage.getCardBalance(getFirtsCard().getIdFirstCard());
        var actualBalanceSecondCard = DashBoardPage.getCardBalance(getSecondCard().getIdSecondCard());
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
        var balanceFirstCard = DashBoardPage.getCardBalance(getFirtsCard().getIdFirstCard());
        var TransferPageError = DashBoardPage.cards(getFirtsCard().getIdFirstCard());
        TransferPageError.transferCards(String.valueOf(amount), DataHelper.getFirtsCard().getNumberFirstCard());
        TransferPageError.error();
    }

    @Test
    void shouldTransferMoreBalance() {
        int amount = 15_000;
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var DashBoardPage = verificationPage.validVerify(verificationCode);
        var balanceSecondCard = DashBoardPage.getCardBalance(getSecondCard().getIdSecondCard());
        var TransferPageError = DashBoardPage.cards(getSecondCard().getIdSecondCard());
        TransferPageError.transferCards(String.valueOf(amount), DataHelper.getFirtsCard().getNumberFirstCard());
        TransferPageError.error();
    }
}

