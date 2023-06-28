import Handlebars from "handlebars/runtime.js";
import ImageStandard from "../image-standard.hbs";
Handlebars.registerPartial("ImageStandard", ImageStandard);

export default {
    title: "Core Components/Image",
    argTypes: {
        altText: {
            control: "text",
        },
        titleText: {
            control: "text",
        },
        cornerRadius: {
            control: "select",
            options: ["top-left", "top-right", "bottom-left", "bottom-right", "none"],
        },
        teaserLogo: {
            control: "boolean",
        }
    },
};

const TemplateImageStandard = ({ label, ...args }) =>
    ImageStandard({ ...args });
export const Image = TemplateImageStandard.bind({});

Image.args = {
    size: "small",
    source: "https://picsum.photos/id/237/200/300",
    cornerRadius: "none",
    teaserLogo: false,
};
