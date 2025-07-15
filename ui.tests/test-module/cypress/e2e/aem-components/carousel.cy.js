/// <reference types="cypress" />
describe(`Carousel: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/carousel/`, () => {
    before(() => {
        cy.visit("/ui-tests/carousel/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Carousel", () => {
        cy.get("#ui-test-carousel .cmp-carousel__actions button").as(
            "previousNextButtons"
        );

        cy.get("#ui-test-carousel .cmp-carousel__indicators li").as(
            "carouselIndicators"
        );

        // First item
        cy.contains("First carousel item", { matchCase: false }).should(
            "be.visible"
        );

        cy.get("@carouselIndicators")
            .eq(0)
            .should("have.class", "cmp-carousel__indicator--active");

        cy.get("@carouselIndicators")
            .eq(1)
            .should("not.have.class", "cmp-carousel__indicator--active");

        cy.get("@previousNextButtons").last().click();

        cy.contains("First carousel item", { matchCase: false }).should(
            "not.be.visible"
        );

        // Second item
        cy.get("#ui-test-carousel-item-2")
            .should("have.attr", "type", "button")
            .contains("second carousel item", { matchCase: false })
            .should("be.visible");

        cy.get("@carouselIndicators")
            .eq(1)
            .should("have.class", "cmp-carousel__indicator--active");

        cy.get("@carouselIndicators")
            .eq(2)
            .should("not.have.class", "cmp-carousel__indicator--active");

        cy.get("@previousNextButtons").last().click();

        cy.contains("second carousel item", { matchCase: false }).should(
            "not.be.visible"
        );

        // Third item
        cy.contains("Third carousel item", { matchCase: false }).should(
            "be.visible"
        );

        cy.get("@carouselIndicators")
            .eq(2)
            .should("have.class", "cmp-carousel__indicator--active");

        cy.get("@carouselIndicators")
            .eq(0)
            .should("not.have.class", "cmp-carousel__indicator--active");

        cy.get("@previousNextButtons").last().click();

        cy.contains("Third carousel item", { matchCase: false }).should(
            "not.be.visible"
        );

        // check previous functionality
        cy.contains("First carousel item", { matchCase: false }).should(
            "be.visible"
        );

        cy.get("@previousNextButtons").first().click();

        cy.contains("Third carousel item", { matchCase: false }).should(
            "be.visible"
        );
    });
});
