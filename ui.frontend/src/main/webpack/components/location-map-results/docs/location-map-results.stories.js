import Handlebars from "handlebars/runtime.js";
import LocationMapResults from "../location-map-results.hbs";

Handlebars.registerPartial("LocationMapResults", LocationMapResults);

export default {
    title: "Components/LocationMapResults",
    argTypes: {},
};

const TemplateLocationMapResults = (args) => LocationMapResults({ ...args });
TemplateLocationMapResults.bind({});

export { TemplateLocationMapResults as MapResults };
