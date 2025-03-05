/// <reference types="cypress" />
describe("Content Collage - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/content-collage/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Content Collage", () => {
        cy.get("#ui-test-content-collage .cmp-container")
            .children()
            .as("contentCollageItems");

        // First image
        cy.get("@contentCollageItems")
            .eq(0)
            .find("img")
            .should("be.visible")
            .and(($img) => {
                expect($img[0].naturalWidth).to.be.greaterThan(
                    0,
                    "The image is not displayed."
                );
            });

        // Second image
        cy.get("@contentCollageItems")
            .eq(1)
            .find("img")
            .should("be.visible") 
            .and(($img) => {
                expect($img[0].naturalWidth).to.be.greaterThan(
                    0,
                    "The image is not displayed."
                );
            });

        // Copy content
        cy.get("@contentCollageItems")
            .eq(2)
            .then(($el) => {
                cy.wrap($el)
                    .contains("LET’S BUILD ON WHAT YOU’VE ACCOMPLISHED", {
                        matchCase: false,
                    })
                    .should("be.visible");
                cy.wrap($el)
                    .contains(
                        "Financial planning and wealth management for those who want more from their wealth.",
                        { matchCase: false }
                    )
                    .should("be.visible");
            });
    });
});
