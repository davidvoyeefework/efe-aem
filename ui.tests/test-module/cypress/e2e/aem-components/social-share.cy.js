/// <reference types="cypress" />
describe(`Social Share: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/social-share/`, () => {
    beforeEach(() => {
        cy.visit("/ui-tests/social-share/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Social Share - Mail & Print", () => {
        // Mail
        cy.get("#ui-test-social-share a")
            .eq(2)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "mailto")
                    .and("include", "/ui-tests/social-share");
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .and(($img) => {
                        expect($img[0].naturalWidth).to.be.greaterThan(
                            0,
                            "Validate the image is displayed"
                        );
                    })
                    .invoke("attr", "src")
                    .should("include", "mail.svg");
            });

        // Print
        cy.get("#ui-test-social-share a")
            .eq(4)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "onclick")
                    .should("include", "window.print()");
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .and(($img) => {
                        expect($img[0].naturalWidth).to.be.greaterThan(
                            0,
                            "Validate the image is displayed"
                        );
                    })
                    .invoke("attr", "src")
                    .should("include", "print.svg");
            });
    });

    it("Social Share - Facebook", () => {
        cy.get("#ui-test-social-share a")
            .eq(0)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "facebook")
                    .and("include", "/ui-tests/social-share");
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .and(($img) => {
                        expect($img[0].naturalWidth).to.be.greaterThan(
                            0,
                            "Validate the image is displayed"
                        );
                    })
                    .invoke("attr", "src")
                    .should("include", "fb.svg");
            });
    });

    it("Social Share - X", () => {
        cy.get("#ui-test-social-share a")
            .eq(1)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "twitter")
                    .and("include", "/ui-tests/social-share");
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .and(($img) => {
                        expect($img[0].naturalWidth).to.be.greaterThan(
                            0,
                            "Validate the image is displayed"
                        );
                    })
                    .invoke("attr", "src")
                    .should("include", "twitter.svg");
            });
    });

    it("Social Share - LinkedIn", () => {
        cy.get("#ui-test-social-share a")
            .eq(3)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "linkedin")
                    .and("include", "/ui-tests/social-share");
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .and(($img) => {
                        expect($img[0].naturalWidth).to.be.greaterThan(
                            0,
                            "Validate the image is displayed"
                        );
                    })
                    .invoke("attr", "src")
                    .should("include", "linkedin.svg");
            });
    });
});
