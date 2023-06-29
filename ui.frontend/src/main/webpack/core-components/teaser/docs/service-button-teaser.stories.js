import Handlebars from "handlebars/runtime.js";
import ServiceButtonTeaser from "../service-button-teaser.hbs";

Handlebars.registerPartial("ServiceButtonTeaser", ServiceButtonTeaser);
export default {
    title: "Core Components/Teaser",
    // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
    argTypes: {
        variant: {
            options: ["service button"],
            control: { type: "radio" },
        },
        text: {
            control: { type: "text" },
        },
    },
};
const TemplateEfeTeaser = ({ label, ...args }) => ServiceButtonTeaser({ ...args });
export const ServiceButtonTeaserHome = TemplateEfeTeaser.bind();
ServiceButtonTeaserHome.args = {
}