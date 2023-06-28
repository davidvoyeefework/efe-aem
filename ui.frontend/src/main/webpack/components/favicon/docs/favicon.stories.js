import Handlebars from "handlebars/runtime.js";
import Favicon from "../favicon.hbs";

Handlebars.registerPartial("Favicon", Favicon);

export default {
    title: "Components/Favicon",
    argTypes: {},
};

const TemplateFavicon = (args) => Favicon({ ...args });
Favicon.bind({});

export { TemplateFavicon as Favicon } ;
