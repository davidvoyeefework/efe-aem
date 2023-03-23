import Handlebars from "handlebars/runtime.js";
import ImageCollage from "../image-collage.hbs";
import ImageStandard from "../../../core-components/image/image-standard.hbs";

Handlebars.registerPartial("ImageCollage", ImageCollage);
Handlebars.registerPartial("ImageStandard", ImageStandard);

export default {
    title: "Components/Image-Collage",
    argTypes: {},
};

const TemplateCollageRight = (args) => ImageCollage({ ...args });
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

ImageCollage.args = {
    "border-top-left": false,
}

export { ImageCollage as Default };
