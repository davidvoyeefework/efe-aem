import Handlebars from "handlebars/runtime.js";
import Button from "../button-linked.hbs";
Handlebars.registerPartial("Button", Button);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Core Components/Links",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    color : {
      options: [ "efe-gray-primary", "efe-red-primary" ],
      control: { type: "select" },
    },
    variant: {
      options: [ "primary-nav-link", "secondary-nav-link", "utility-nav-link", "body-01-link", "body-02-link", "body-03-link"],
      control: { type: "radio" },
    },
  },
};

const TemplatePrimary = ({ label, ...args }) => Button({ ...args });
export const NavLinks = TemplatePrimary.bind();
NavLinks.args = {
  variant: "primary-nav-link",
  color: "efe-gray-primary"
}
