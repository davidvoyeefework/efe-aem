import Handlebars from "handlebars/runtime.js";
import FePage from "../fe-page.hbs";

Handlebars.registerPartial("FePage", FePage);
export default {
    title: "pages/FePage",
    argTypes: {
        sponsor: {
            options: ["Boeing", "ATT"],
            control: { type: "radio" },
        },
    },
};

export { FePage as Default };
