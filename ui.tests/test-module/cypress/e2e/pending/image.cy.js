/// <reference types="cypress" />
describe("Image - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/image/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Image", () => {
        cy.get("#ui-test-image img")
            .should("be.visible")
            .and(($img) => {
                expect($img[0].naturalWidth).to.be.greaterThan(
                    0,
                    "Validate the image is displayed"
                );
            });
    });
});
