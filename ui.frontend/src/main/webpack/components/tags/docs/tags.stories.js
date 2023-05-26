import Handlebars from "handlebars/runtime.js";
import Tags from "../tags.hbs";

Handlebars.registerPartial("Tags", Tags);
export default {
    title: "Components/Tags",
    argTypes: {},
};

export { Tags as Default };
