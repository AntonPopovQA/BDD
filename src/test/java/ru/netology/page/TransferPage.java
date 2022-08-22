package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {

    private SelenideElement firstCard = $("[data-test-id=92df3f1c-a033-48e6-8390-206f6b1f56c0]");
    private SelenideElement secondCard = $("[data-test-id=0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement toField = $("[data-test-id=to] input");
    private SelenideElement transferCards = $x("//*[contains(text(), 'Пополнение карты')]");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id='error-notification']");

    public TransferPage() {
        transferCards.shouldBe(visible);
    }

    public DashboardPage transferCards(String value, String fromCard) {
        amountField.setValue(value);
        fromField.setValue(fromCard);
        transferButton.click();
        return new DashboardPage();
    }

    public void error() {
        error.shouldBe(visible);
    }
}
