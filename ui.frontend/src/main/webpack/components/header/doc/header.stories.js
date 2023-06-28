import Handlebars from "handlebars/runtime.js";
import Header from "../header.hbs";
Handlebars.registerPartial("Header", Header);
export default {
  title: "Components/Header/EFE Global Nav",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {},
};

export { Header };
