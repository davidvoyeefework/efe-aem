import Handlebars from "handlebars/runtime.js";
import FeLayout from "../fe-layout.hbs";
import FeLayout2 from "../fe-layout-2.hbs";
import FeLayoutWithImage from "../fe-layout-with-image.hbs";

Handlebars.registerPartial("FeLayout", FeLayout);
Handlebars.registerPartial("FeLayout2", FeLayout2);
Handlebars.registerPartial("FeLayoutWithImage", FeLayoutWithImage);
export default {
    title: "FE Components/FeLayout",
    argTypes: {
    },
};

export { FeLayout as Default };
export { FeLayout2 as FeLayout2 };
export { FeLayoutWithImage as FeLayoutWithImage };
