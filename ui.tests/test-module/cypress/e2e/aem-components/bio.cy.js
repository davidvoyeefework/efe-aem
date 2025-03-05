/// <reference types="cypress" />
describe("Bio - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/bio/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Bio", () => {
        cy.get("#ui-test-bio script[type='application/ld+json']").then(
            ($el) => {
                let bio_info = JSON.parse($el.html());

                cy.get("#ui-test-bio img")
                    .first()
                    .should("be.visible")
                    .and(($img) => {
                        expect($img[0].naturalWidth).to.be.greaterThan(0, "The image is not displayed.");
                    });

                cy.get("#ui-test-bio")
                    .contains(bio_info.name, { matchCase: false })
                    .should("be.visible");
                cy.get("#ui-test-bio")
                    .contains(bio_info.jobTitle, { matchCase: false })
                    .should("be.visible");
            }
        );
    });
});
