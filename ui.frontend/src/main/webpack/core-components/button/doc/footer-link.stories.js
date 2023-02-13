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
    variant: {
      options: [ "footer-01", "footer-02" ],
      control: { type: "radio" },
    },
    color: {
      options: [ "efe-gray-primary", "efe-gray-new"],
      control: { type: "radio" },
    },

    link: {
      control: { type: "text" },
    },
  },
};

const TemplatePrimary = ({ label, ...args }) => Button({ ...args });
export const FooterLinks = TemplatePrimary.bind();
FooterLinks.args = {
  variant: "footer-01",
  color: "efe-gray-primary",
  text: "connect with a planner",
  link: "#"
}
