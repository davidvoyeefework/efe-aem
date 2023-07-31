import Handlebars from "handlebars/runtime.js";
import FeHeader from "../fe-header.hbs";

Handlebars.registerPartial("FeHeader", FeHeader);
export default {
    title: "FE/FeHeader",
    argTypes: {
    },
};

export { FeHeader as Default };
