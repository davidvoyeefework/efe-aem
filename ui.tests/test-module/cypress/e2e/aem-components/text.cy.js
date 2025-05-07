/// <reference types="cypress" />
describe("Text - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/text/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Text", () => {
        cy.get("#ui-test-text").then(($el) => {
            cy.wrap($el)
                .find("p")
                .contains("special offer", { matchCase: false })
                .should("be.visible");
            cy.wrap($el)
                .find("h2")
                .contains("your sign to start planning is here", {
                    matchCase: false,
                })
                .should("be.visible");
            cy.wrap($el)
                .find("h3")
                .contains(
                    "Because for a limited time, you can meet with a financial planner and get a personalized plan – free. Ends Jan. 31.",
                    { matchCase: false }
                )
                .should("be.visible");
        });
    });
});
