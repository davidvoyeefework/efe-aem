import Handlebars from "handlebars/runtime.js";
import FePage from "../fe-page.hbs";

Handlebars.registerPartial("FePage", FePage);
const LOGOS = [
    'https://www.edelmanfinancialengines.com/content/dam/efe/corporate-brand/Brand/Logos/EFE-Logos/Primary/_other/logo%20efe%20.svg',
    'https://landingpages.financialengines.com/content/dam/efe/employer/logos/web/ford_ub.png',
    'https://landingpages.financialengines.com/content/dam/efe/employer/logos/web/boeingxrx_ub.png',
    'https://landingpages.financialengines.com/content/dam/efe/employer/logos/web/covington_ub.png'];
export default {
    title: "pages/FePage",
    argTypes: {
        rkId: {
            options: ["theme-aon", "theme-vanguard","theme-ssga"],
            control: { type: "radio" },
        },
        headerType: {
            options: ["direct","subadvised"],
            control: { type: "inline-radio" }
        },
        primaryLogo: {
            options: LOGOS,
            control: { type: "select" }
        },
        secondaryLogo: {
            options: LOGOS,
            control: { type: "select" }
        }
    }
};

export { FePage as Default };
