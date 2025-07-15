/// <reference types="cypress" />
describe(`Bio: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/bio/`, () => {
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
                    // .and(($img) => {
                    //     expect($img[0].naturalWidth).to.be.greaterThan(0, "Validate the image is displayed.");
                    // });

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
