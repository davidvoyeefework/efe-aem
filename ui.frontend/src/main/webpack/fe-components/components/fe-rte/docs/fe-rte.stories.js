import Handlebars from "handlebars/runtime.js";
import Ferte from "../fe-rte.hbs";

Handlebars.registerPartial("Ferte", Ferte);
export default {
    title: "FE Components/FeRte",
    argTypes: {
    },
};

export { Ferte as Default };
