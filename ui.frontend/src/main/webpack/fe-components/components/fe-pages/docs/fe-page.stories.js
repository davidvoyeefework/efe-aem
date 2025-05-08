import Handlebars from "handlebars/runtime.js";
import FePage from "../fe-page.hbs";

Handlebars.registerPartial("FePage", FePage);
export default {
    title: "pages/FePage",
    argTypes: {
        rkId: {
            options: ["theme-aon", "theme-vanguard","theme-ssga"],
            control: { type: "radio" },
        },
        subadvised: {
            options: [true],
            control: { type: "boolean" }
        }
    },
};

export { FePage as Default };
