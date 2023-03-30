import Handlebars from "handlebars/runtime.js";
import ContentCollage from "../content-collage.hbs";
import ImageStandard from "../../../core-components/image/image-standard.hbs";
import CoreTextComponent from "../../../core-components/text/text.hbs";

Handlebars.registerPartial("ContentCollage", ContentCollage);
Handlebars.registerPartial("ImageStandard", ImageStandard);
Handlebars.registerPartial("CoreTextComponent", CoreTextComponent);

export default {
    title: "Components/Content-Collage",
    argTypes: {},
    args: {
        "border-top-left": false,
    }
};

export { ContentCollage as Default };
