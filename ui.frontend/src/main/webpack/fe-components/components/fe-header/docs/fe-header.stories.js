import Handlebars from "handlebars/runtime.js";
import FeHeader from "../fe-header.hbs";

Handlebars.registerPartial("FeHeader", FeHeader);
export default {
    title: "FE Components/FeHeader",
    argTypes: {
        headerType: {
            options: ["direct","subadvised"],
            control: { type: "inline-radio" }
        },
        primaryLogo: {
            control: { type: "text" }
        },
        secondaryLogo: {
            control: { type: "text" }
        }
    },
};

export { FeHeader as Default };
