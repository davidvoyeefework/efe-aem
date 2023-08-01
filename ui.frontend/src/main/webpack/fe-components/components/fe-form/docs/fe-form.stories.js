import Handlebars from "handlebars/runtime.js";
import Form from "../fe-form.hbs";

Handlebars.registerPartial("Form", Form);
export default {
    title: "EFE Unbounce/form",
    argTypes: {
        sponsor: {
            options: ["Boeing", "ATT"],
            control: { type: "radio" },
        },
    },
};

export { Form as Default };
