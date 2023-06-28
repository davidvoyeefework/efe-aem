import Handlebars from "handlebars/runtime.js";
import LinkedButton from "../button-linked.hbs";
Handlebars.registerPartial("BodyButton", LinkedButton);
export default {
  title: "Core Components/Links",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    color : {
      options: [ "efe-gray-primary", "efe-red-primary" ],
      control: { type: "radio" },
    },
    variant: {
      options: ["body-01-link", "body-02-link", "body-03-link"],
      control: { type: "radio" },
    },
    link: {
      control: { type: "text" },
    },
  },
};

const TemplatePrimary = ({ label, ...args }) => LinkedButton({ ...args });
export const BodyLinks = TemplatePrimary.bind();
BodyLinks.args = {
  variant: "primary-nav-link",
  color: "efe-gray-primary",
  link: "#",
  text: "Edelman Financial Engines Typography styles"
}
