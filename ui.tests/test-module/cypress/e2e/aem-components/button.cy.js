/// <reference types="cypress" />
describe("Button - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/button/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Button", () => {
        // Primary CTA
        cy.get("#primary-cta")
            .should("have.attr", "aria-label", "primary cta label")
            .and("be.visible")
            .contains("Primary CTA", { matchCase: false });
        
        // Secondary CTA
        cy.get("#secondary-cta")
            .should("have.attr", "aria-label", "secondary cta label")
            .and("be.visible")
            .contains("Secondary CTA", { matchCase: false });

        // Primary Link
        cy.get("#primary-link")
            .should("have.attr", "aria-label", "primary link label")
            .should("have.attr", "href", "/")
            .and("be.visible")
            .contains("Primary Link", { matchCase: false });
        
        // Secondary Link
        cy.get("#secondary-link")
            .should("have.attr", "aria-label", "secondary link label")
            .should("have.attr", "href", "/approach/")
            .and("be.visible")
            .contains("Secondary Link", { matchCase: false });
    });
});
