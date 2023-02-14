import Handlebars from "handlebars/runtime.js";
import Button from "../button-standard.hbs";
Handlebars.registerPartial("CTA", Button);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Core Components/Buttons",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["cta-primary"],
      control: { type: "radio" },
    },
    link: {
      control: { type: "text" },
    }
  },
};

const TemplatePrimary = ({ label, ...args }) => Button({ ...args });
export const CallToActions = TemplatePrimary.bind();
CallToActions.args = {
  variant: "cta-primary",
  link: "#",
  text: "connect with a planner"
}
