import Handlebars from "handlebars/runtime.js";
import LocationMapDirections from "../location-map-directions.hbs";

Handlebars.registerPartial("LocationMapDirections", LocationMapDirections);

export default {
    title: "Components/LocationMapDirections",
    argTypes: {},
};

const TemplateLocationMapDirections = (args) => LocationMapDirections({ ...args });
TemplateLocationMapDirections.bind({});

export { TemplateLocationMapDirections as MapDirections };
