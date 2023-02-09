import Handlebars from "handlebars/runtime.js";
import Nav from "../nav-primary.hbs";
Handlebars.registerPartial("Nav", Nav);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Components/Navigation",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {

  },
};

const GlobalNav = ({ label, ...args }) => Nav({ ...args });
export const NavPrimary = GlobalNav.bind();
NavPrimary.args = {
    variant: "primary",
}
