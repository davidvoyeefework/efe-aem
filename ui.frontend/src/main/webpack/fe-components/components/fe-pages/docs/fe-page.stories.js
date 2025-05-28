import Handlebars from "handlebars/runtime.js";
import FePage from "../fe-page.hbs";

Handlebars.registerPartial("FePage", FePage);
const LOGOS = ['EFE','Ford','Boeing','Covington','None'];
const LOGOMAP = {
    'EFE': 'https://www.edelmanfinancialengines.com/content/dam/efe/corporate-brand/Brand/Logos/EFE-Logos/Primary/_other/logo%20efe%20.svg',
    'Ford': 'https://landingpages.financialengines.com/content/dam/efe/employer/logos/web/ford_ub.png',
    'Boeing': 'https://landingpages.financialengines.com/content/dam/efe/employer/logos/web/boeingxrx_ub.png',
    'Covington': 'https://landingpages.financialengines.com/content/dam/efe/employer/logos/web/covington_ub.png',
    'None': ''
};
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
            mapping: LOGOMAP,
            control: {
                type: "select",
                labels: LOGOS
            }
        },
        secondaryLogo: {
            options: LOGOS,
            mapping: LOGOMAP,
            control: { type: "select" }
        }
    }
};

export { FePage as Default };
