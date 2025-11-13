/// <reference types="cypress" />
describe(`Comparison Card: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/comparison-card/`, () => {
    before(() => {
        cy.visit("/ui-tests/comparison-card/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Comparison Card", () => {
        cy.get("#ui-test-comparison-cards .comparisoncard").as("comparisonCards");

        cy.get("@comparisonCards")
            .eq(0)
            .then(($el) => {
                cy.wrap($el).should("have.class", "blue").should("be.visible");
                cy.wrap($el)
                    .find(".header")
                    .contains("6 strategies for retirement income", {
                        matchCase: false,
                    })
                    .should("be.visible");
                cy.wrap($el)
                    .find(".body")
                    .contains("Comparison Card", { matchCase: false })
                    .should("be.visible");
            });

        cy.get("@comparisonCards")
            .eq(1)
            .then(($el) => {
                cy.wrap($el).should("have.class", "gray").should("be.visible");
                cy.wrap($el)
                    .find(".header")
                    .contains("Manage your own account", { matchCase: false })
                    .should("be.visible");
                cy.wrap($el)
                    .find(".body button")
                    .should("have.attr", "type", "button")
                    .contains("Comparison Card", { matchCase: false })
                    .should("be.visible");
            });

        cy.get("@comparisonCards")
            .eq(2)
            .then(($el) => {
                cy.wrap($el).should("have.class", "teal").should("be.visible");
                cy.wrap($el)
                    .find(".header")
                    .contains("What other companies offer", {
                        matchCase: false,
                    })
                    .should("be.visible");
                cy.wrap($el)
                    .find(".body ul li")
                    .should("have.length", 3)
                    .should("be.visible");
            });
    });
});
