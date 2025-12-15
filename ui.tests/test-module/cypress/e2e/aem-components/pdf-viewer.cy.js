/// <reference types="cypress" />
describe(`PDF Viewer: AEM Component - ${Cypress.config(
    "baseUrl"
)}/ui-tests/pdf-viewer/`, () => {
    const getIframeDocument = () => {
        return (
            cy
                .get("[id^='iframe-pdfviewer']")
                // Cypress yields jQuery element, which has the real
                // DOM element under property "0".
                // From the real DOM iframe element we can get
                // the "document" element, it is stored in "contentDocument" property
                // Cypress "its" command can access deep properties using dot notation
                // https://on.cypress.io/its
                .its("0.contentDocument")
                .should("exist")
        );
    };

    const getIframeBody = () => {
        // get the document
        return (
            getIframeDocument()
                // automatically retries until body is loaded
                .its("body")
                .should("not.be.undefined")
                // wraps "body" DOM element to allow
                // chaining more Cypress commands, like ".find(...)"
                .then(cy.wrap)
        );
    };

    before(() => {
        cy.visit("/ui-tests/pdf-viewer/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("PDF Viewer", () => {
        getIframeBody().contains("EVERYDAY WEALTH, EVERYDAY INSIGHTS", {
            matchCase: false,
        });
        getIframeBody().contains("NAVIGATING RELATIONSHIPS", {
            matchCase: false,
        });
    });
});
