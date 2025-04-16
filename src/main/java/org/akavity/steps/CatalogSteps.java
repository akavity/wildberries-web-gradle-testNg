package org.akavity.steps;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.akavity.pages.CatalogPage;
import org.akavity.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.codeborne.selenide.Condition.visible;

@Log4j2
public class CatalogSteps {
    CatalogPage catalogPage = new CatalogPage();

    @Step
    public String extractTextFromTitle() {
        String title = catalogPage.getTileField().shouldBe(visible).text();
        log.info("Catalog title: {}", title);
        return title;
    }

    @Step
    public boolean areProductPricesWithinLimit(String min, String max) {
        log.info("Check product prices \n min price: {} \n max price: {}", min, max);
        Utils.sleep(1500);
        ElementsCollection prices = catalogPage.getPricesFields();
        Predicate<Double> predicate = p -> (p >= Integer.parseInt(min) && p <= Integer.parseInt(max));
        return Utils.relationalMethod(prices, predicate);
    }

    @Step
    public boolean doProductNamesContainText(String text) {
        catalogPage.getSearchTitleField(text).shouldBe(visible);
        log.info("Do product names contain text: {}", text);
        List<String> names = new ArrayList<>(catalogPage.getProductNames().texts());
        boolean result;
        log.info("List size: {}", names.size());
        if (names.isEmpty()) {
            log.info("List is empty");
            result = false;
        } else {
            result = names.stream()
                    .peek(d -> log.info("Product name: {}", d))
                    .allMatch(name -> name.toLowerCase().contains(text.toLowerCase()));
        }
        return result;
    }

    @Step
    public boolean isCurrencyCorrect(String cur) {
        log.info("Check that price contains currency: {}", cur);
        catalogPage.getPricesFields().first().shouldBe(visible);
        List<String> prices = new ArrayList<>(catalogPage.getPricesFields().texts());
        boolean result;
        if (prices.isEmpty()) {
            log.info("Collection is empty");
            result = false;
        } else {
            result = prices.stream()
                    .peek(p -> log.info("Element price: {}", p))
                    .allMatch(el -> el.contains(cur));
        }
        return result;
    }

    @Step
    public void selectFirstProductCard() {
        log.info("Select a first product card");
        catalogPage.getProductCards().first().click();
    }

    @Step
    public void clickFirstPopupButton() {
        log.info("Hover over the first product card");
        catalogPage.getProductCards().first().hover();
        Utils.sleep(1000);
        log.info("Click \"Popup\" button");
        catalogPage.getPopupButtons().first().click();
    }

    @Step
    public String getFirstProductCardBrand() {
        String brand = catalogPage.getBrandFields().first().getText();
        log.info("Brand of the first product card: {}", brand);
        return brand;
    }

    @Step
    public double getFirstProductCardPrice() {
        double price = Utils.extractDoubleFromText(catalogPage.getPricesFields().first().text());
        log.info("Price of the first product card: {}", price);
        return price;
    }

    @Step
    public void clickFirstButtonAddToBasket() {
        log.info("Click on the first button \"Add to basket\"");
        catalogPage.getAddBasketButtons().first().click();
    }

    @Step
    public void clickFirstButtonOfSizeList() {
        Utils.sleep(1500);
        boolean popUp = catalogPage.getPopupBlock().isDisplayed();
        if (popUp) {
            log.info("Popup block is displayed. Click first button of size list");
            catalogPage.getSizeListButtons().first().click();
        } else {
            log.info("Popup block isn't displayed");
        }
    }

    @Step
    public boolean checkSortByAscendingPrice(int elements) {
        Utils.sleep(1500);
        boolean result = Utils.isSortedAscending(getProductPrices(elements));
        log.info("Are first \" {} \" products sorted correctly in ascending price order?: {}", elements, result);
        return result;
    }

    @Step
    public boolean checkSortByDecreasingPrice(int elements) {
        Utils.sleep(1500);
        boolean result = Utils.isSortedDecreasing(getProductPrices(elements));
        log.info("Are first \" {} \" products sorted correctly in decreasing price order?: {}", elements, result);
        return result;
    }

    @Step
    public boolean checkNewTips(int elements) {
        Utils.sleep(1500);
        return catalogPage.getNewTips().first(elements).stream()
                .map(SelenideElement::getText)
                .peek(x -> log.info("tip contain text: {}", x))
                .allMatch(x -> x.equalsIgnoreCase("new"));
    }

    @Step
    public boolean checkRatingTips(int elements, double minRating) {
        Utils.sleep(1500);
        return catalogPage.getProductRatings().first(elements).stream()
                .map(SelenideElement::getText)
                .map(Utils::extractDoubleFromText)
                .peek(x -> log.info("tip contain rating: {}", x))
                .allMatch(x -> x >= minRating);
    }

    @Step
    public boolean checkSaleTips(int elements) {
        Utils.sleep(1500);
        return catalogPage.getSaleTips().first(elements).stream()
                .map(SelenideElement::getText)
                .peek(x -> log.info("Sale tip contains: {}", x))
                .allMatch(x -> x.contains("%"));
    }

    private List<Double> getProductPrices(int elements) {
        return catalogPage.getPricesFields().first(elements).texts().stream()
                .map(Utils::extractDoubleFromText)
                .peek(x -> log.info("product price: {}", x)).toList();
    }
}
