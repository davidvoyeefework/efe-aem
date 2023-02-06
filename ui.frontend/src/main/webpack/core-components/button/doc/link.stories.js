import Handlebars from "handlebars/runtime.js";
import "../../../site/main.scss";
import Button from "../button.hbs";
Handlebars.registerPartial("Button", Button);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Core Components/Links",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: [ "primary-nav-link", "primary-nav-link-red", "secondary-nav-link", "utility-nav-link", "utility-nav-link-red", "body-01-link", "body-01-link-red", "body-02-link", "body-02-link-red", "body-03-link", "body-03-link-red"],
      control: { type: "radio" },
    },
  },
};

const TemplatePrimary = ({ label, ...args }) => Button({ ...args });
export const NavLinks = TemplatePrimary.bind();
NavLinks.args = {
  variant: "primary-nav-link",
}
