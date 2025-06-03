import Handlebars from "handlebars/runtime.js";
import LocationMapState from "../location-map-state.hbs";

Handlebars.registerPartial("LocationMapState", LocationMapState);

export default {
  title: "Components/LocationMapState",
  argTypes: {},
};

const TemplateLocationMapState = (args) => LocationMapState({ ...args });
TemplateLocationMapState.bind({});
TemplateLocationMapState.args = {
  "no-results": false,
};

export { TemplateLocationMapState as MapResults };
