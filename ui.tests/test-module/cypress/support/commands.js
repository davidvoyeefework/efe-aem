Cypress.on("uncaught:exception", (err, runnable) => {
    console.log("Uncaught Exception: " + err.message);
    return false;
});