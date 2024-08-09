import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.InvalidStateError;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class WbSelenideScriptTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
    }

    @AfterAll
    public static void tearDown() {
        closeWebDriver();
    }

    @Test
    public void testAddToCart() {
        open("https://www.wildberries.ru/");

        $("#searchInput").setValue("мобильный телефон").pressEnter();

        SelenideElement product = $x("//*[@class='product-card-list']/article[1]");
        try {
            product.click();
        } catch (InvalidStateError e) {
            $x("//*[@class='autocomplete__scroll-container']/ul[1]").click();
            product.click();
        }

        SelenideElement addToCartButton = $x("//*[@class='product-page__order-buttons']//*[@class='order__button btn-main']");
        addToCartButton.shouldBe(visible).click();

        SelenideElement cartLink = $("a[data-wba-header-name='Cart']");
        cartLink.shouldBe(visible).click();

        SelenideElement addedItem = $(".list-item__good");
        addedItem.shouldBe(visible);
    }
}