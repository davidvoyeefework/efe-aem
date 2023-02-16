import Handlebars from "handlebars/runtime.js";
import FooterLinkedButton from "../button-linked.hbs";
Handlebars.registerPartial("FooterLinkedButton", FooterLinkedButton);
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
  },
};

const TemplatePrimary = ({ label, ...args }) => FooterLinkedButton({ ...args });
export const FooterLinks = TemplatePrimary.bind();
FooterLinks.args = {
  variant: "footer-01",
}
