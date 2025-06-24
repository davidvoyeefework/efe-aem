/// <reference types="cypress" />
describe("Related Article Ed Center - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/related-article-ed-center/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Related Article Ed Center", () => {
        cy.get("#ui-test-related-article-ed-center li").each(($article) => {
            cy.wrap($article).find("a").should("be.visible");
            if (
                !$article
                    .find(".cmp-teaser__image")
                    .hasClass("thumbnail-placeholder")
            ) {
                cy.wrap($article)
                    .find("img")
                    .should("be.visible")
                    .invoke("outerHeight")
                    .should("be.gt", 100);
                cy.wrap($article)
                    .find("img")
                    .invoke("outerWidth")
                    .should("be.gt", 100);
            }
        });
    });
});
