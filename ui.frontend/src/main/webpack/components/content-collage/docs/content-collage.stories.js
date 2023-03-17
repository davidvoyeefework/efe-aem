import Handlebars from "handlebars/runtime.js";
import ContentCollage from "../content-collage.hbs";
import ImageStandard from "../../../core-components/image/image-standard.hbs";

Handlebars.registerPartial("ContentCollage", ContentCollage);
Handlebars.registerPartial("ImageStandard", ImageStandard);

export default {
    title: "Components/Content-Collage",
    argTypes: {},
};

const TemplateCollageRight = (args) => ContentCollage({ ...args });
export const RightLarge = TemplateCollageRight.bind({});
RightLarge.args = {
    variation: "right-large",
};

RightLarge.argTypes = {
    variant: {
        options: ["border-top-left", "border-multiple"],
        control: {
            type: "radio",
        },
    },
};

ContentCollage.args = {
    "border-top-left": false,
}

export { ContentCollage as Default };
