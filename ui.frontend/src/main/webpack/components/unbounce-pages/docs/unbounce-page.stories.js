import Handlebars from "handlebars/runtime.js";
import UnbouncePage from "../unbounce-page.hbs";

Handlebars.registerPartial("UnbouncePage", UnbouncePage);
export default {
    title: "pages/UnbouncePage",
    argTypes: {
        sponsor: {
            options: ["Boeing", "ATT"],
            control: { type: "radio" },
        },
    },
};

export { UnbouncePage as Default };
