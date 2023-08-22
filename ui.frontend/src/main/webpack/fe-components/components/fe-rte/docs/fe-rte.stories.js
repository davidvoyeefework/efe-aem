import Handlebars from "handlebars/runtime.js";
import Ferte from "../fe-rte.hbs";
import Typography from "../fe-rte-typography.hbs";

Handlebars.registerPartial("Ferte", Ferte);
Handlebars.registerPartial("Typography", Typography);
export default {
    title: "FE Components/FeRte",
    argTypes: {
        variation: {
            options: ["fe-layout-unordered-list", "fe-check-unordered-list", "fe-dot-unordered-list", "fe-charcoal-check-unordered-list"],
            control: { type: "radio" },
        },
        addOnClass: {
            options: ["font-color-red"],
            control: { type: "radio" },
        },
    },
    args:{
        variation:"fe-layout-unordered-list",
    }
};
const Templaterte = ({ label, ...args }) => Ferte({...args});
export const Rte = Templaterte.bind();

const TemplaterteTypoGraphy = ({ label, ...args }) => Typography({...args});
export const TypographyExample = TemplaterteTypoGraphy.bind();

// export { Ferte as Default };
