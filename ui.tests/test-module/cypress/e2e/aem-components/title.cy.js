/// <reference types="cypress" />
describe("Title - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/title/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Title", () => {
        cy.get("#ui-test-title").then(($el) => {
            cy.wrap($el)
                .find("h2")
                .contains("h2 title with heading 1", { matchCase: false })
                .should("be.visible");
            cy.wrap($el)
                .find("h3")
                .contains("h3 title with sub heading 1", { matchCase: false })
                .should("be.visible");
            cy.wrap($el)
                .find("h4")
                .contains("h4 title with link", { matchCase: false })
                .should("be.visible")
                .should("have.attr", "href", "/");
        });
    });
});
