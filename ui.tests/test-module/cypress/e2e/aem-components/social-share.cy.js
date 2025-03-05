/// <reference types="cypress" />
describe("Social Share - AEM Component", () => {
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
                    .and(
                        "include",
                        `${Cypress.config("baseUrl")}/ui-tests/social-share`
                    );
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .then(($img) => {
                        cy.wrap($img)
                            .invoke("attr", "src")
                            .should("include", "mail.svg");
                        cy.wrap($img).invoke("outerHeight").should("be.gt", 15);
                        cy.wrap($img).invoke("outerWidth").should("be.gt", 15);
                    });
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
                    .then(($img) => {
                        cy.wrap($img)
                            .invoke("attr", "src")
                            .should("include", "print.svg");
                        cy.wrap($img).invoke("outerHeight").should("be.gt", 15);
                        cy.wrap($img).invoke("outerWidth").should("be.gt", 15);
                    });
            });
    });

    it("Social Share - Facebook", () => {
        cy.get("#ui-test-social-share a")
            .eq(0)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "facebook")
                    .and(
                        "include",
                        `${Cypress.config("baseUrl")}/ui-tests/social-share`
                    );
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .then(($img) => {
                        cy.wrap($img)
                            .invoke("attr", "src")
                            .should("include", "fb.svg");
                        cy.wrap($img).invoke("outerHeight").should("be.gt", 15);
                        cy.wrap($img).invoke("outerWidth").should("be.gt", 15);
                    });

                cy.wrap($a).click();
                cy.url().should("include", "facebook.com");
            });
    });

    it("Social Share - X", () => {
        cy.get("#ui-test-social-share a")
            .eq(1)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "twitter")
                    .and(
                        "include",
                        `${Cypress.config("baseUrl")}/ui-tests/social-share`
                    );
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .then(($img) => {
                        cy.wrap($img)
                            .invoke("attr", "src")
                            .should("include", "twitter.svg");
                        cy.wrap($img).invoke("outerHeight").should("be.gt", 15);
                        cy.wrap($img).invoke("outerWidth").should("be.gt", 15);
                    });

                cy.wrap($a).click();
                cy.url().should("include", "x.com");
            });
    });

    it("Social Share - LinkedIn", () => {
        cy.get("#ui-test-social-share a")
            .eq(3)
            .then(($a) => {
                cy.wrap($a)
                    .invoke("attr", "href")
                    .should("include", "linkedin")
                    .and(
                        "include",
                        `${Cypress.config("baseUrl")}/ui-tests/social-share`
                    );
                cy.wrap($a)
                    .find("img.cmp-social-share__default-img")
                    .then(($img) => {
                        cy.wrap($img)
                            .invoke("attr", "src")
                            .should("include", "linkedin.svg");
                        cy.wrap($img).invoke("outerHeight").should("be.gt", 15);
                        cy.wrap($img).invoke("outerWidth").should("be.gt", 15);
                    });

                cy.wrap($a).click();
                cy.url().should("include", "linkedin.com");
            });
    });
});
