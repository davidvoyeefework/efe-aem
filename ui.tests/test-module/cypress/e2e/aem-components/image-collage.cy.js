/// <reference types="cypress" />
describe(`Image Collage: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/image-collage/`, () => {
    before(() => {
        cy.visit("/ui-tests/image-collage/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Image Collage", () => {
        cy.get("#ui-test-image-collage .image").as("collageImages");

        cy.get("@collageImages")
            .eq(0)
            .find("img")
            .should("be.visible")
            .and(($img) => {
                expect($img[0].naturalWidth).to.be.greaterThan(
                    0,
                    "Validate the image is displayed"
                );
            });

        cy.get("@collageImages")
            .eq(1)
            .find("img")
            .should("be.visible")
            .and(($img) => {
                expect($img[0].naturalWidth).to.be.greaterThan(
                    0,
                    "Validate the image is displayed"
                );
            });

        cy.get("@collageImages")
            .eq(2)
            .find("img")
            .should("be.visible")
            .and(($img) => {
                expect($img[0].naturalWidth).to.be.greaterThan(
                    0,
                    "Validate the image is displayed"
                );
            });
    });
});
