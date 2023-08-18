import Handlebars from "handlebars/runtime.js";
import Ferte from "../fe-rte.hbs";

Handlebars.registerPartial("Ferte", Ferte);
export default {
    title: "FE Components/FeRte",
    argTypes: {
        variation: {
            options: ["fe-layout-unordered-list", "fe-check-unordered-list", "fe-dot-unordered-list" ],
            control: { type: "radio" },
        },
    },
    args:{
        variation:"fe-layout-unordered-list",
    }
};
const Templaterte = ({ label, ...args }) => Ferte({...args});
export const Rte = Templaterte.bind();

// export { Ferte as Default };
