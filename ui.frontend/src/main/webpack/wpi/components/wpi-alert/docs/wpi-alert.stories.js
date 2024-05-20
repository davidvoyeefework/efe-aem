import Alert from "../wpi-alert.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("Alert", Alert);
export default {
    title: "FE Components/Wpi Alert",
    argTypes: {
        theme: {
            options: ["vanguard", "fidelity"],
            control: { type: "radio" },
        },
    },
};

const TemplateAlert = ({ label, ...args }) => Alert({...args});
export const WpiAlert = TemplateAlert.bind();

