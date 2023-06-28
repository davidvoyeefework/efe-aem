import Handlebars from "handlebars/runtime.js";
import VerticalSeparatorForStats from "../vertical-separator.hbs";
import VerticalSeparatorForImages from "../vertical-separator-images.hbs";

Handlebars.registerPartial("verticalSeparatorStats", VerticalSeparatorForStats);

export default {
    title: "Components/VerticalSeparator",
    argTypes: {
        align: {
            options: ["middle", "top", "bottom"],
            control: { type: "radio" },
        },
        horizontalSeparator: {
            control: "boolean",
        },
        custom: {
            control: "boolean",
        }
    },
};

const TemplateVerticalSeparatorStats = ({ label, ...args }) => VerticalSeparatorForStats({ ...args });
export const stats = TemplateVerticalSeparatorStats.bind({});
stats.args = {
    horizontalSeparator: "true",
    custom: "true",
}

const TemplateVerticalSeparatorWithImages = ({ label, ...args }) =>VerticalSeparatorForImages({ ...args });
export const WithImages = TemplateVerticalSeparatorWithImages.bind({});
WithImages.args = {
    align: "top",
    horizontalSeparator: "true",
}
