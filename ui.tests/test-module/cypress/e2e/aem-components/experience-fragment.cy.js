/// <reference types="cypress" />
describe(`Experience Fragment: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/experience-fragment/`, () => {
    before(() => {
        cy.visit("/ui-tests/experience-fragment/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Experience Fragment", () => {
        cy.get("#ui-test-experience-fragment").then(($el) => {
            cy.wrap($el)
                .find("h2")
                .contains("UI Tests for XF Component", { matchCase: false })
                .should("be.visible");
            cy.wrap($el)
                .find("p").as("paragraphs")

                cy.get("@paragraphs").first()
                .contains(
                    "Financial planning and wealth management for those who want more from their wealth.",
                    { matchCase: false }
                )
                .should("be.visible");
                cy.get("@paragraphs").last()
                .contains(
                    "You’ve worked hard to get where you are. But you know that there’s more to do. We can help. With our financial advisors, you’ll gain a modern wealth planning relationship offering integrated advice and strategies to help you elevate your financial potential.",
                    { matchCase: false }
                )
                .should("be.visible");
        });
    });
});
