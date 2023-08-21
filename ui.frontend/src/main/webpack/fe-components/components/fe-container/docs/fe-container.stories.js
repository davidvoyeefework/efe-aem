import Handlebars from "handlebars/runtime.js";
import Container from "../fe-container.hbs";

Handlebars.registerPartial("Container", Container);
export default {
    title: "FE Components/Container",
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
const TemplateContainer = ({ label, ...args }) => Container({...args});
export const container = TemplateContainer.bind();

// export { Ferte as Default };
