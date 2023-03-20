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
      options: ["button-primary", "button-secondary", "link-primary", "link-secondary", "icon-button"],
      control: { type: "radio" },
    },
    link: {
      control: { type: "text" },
    },
    position: {
      options: ["unset", "right", "center"],
      control: { type: "radio" },
    },
    buttonstate: {
      options: ["default", "hover", "disabled"],
      control: { type: "radio" },
    }, 
    texttransform: {
      options: ["default", "sentence-case"],
      control: { type: "radio" },
    }
  }
};

const TemplatePrimary = ({ label, ...args }) => Button({ ...args });
export const PrimarySecondary = TemplatePrimary.bind();
PrimarySecondary.args = {
  variant: "button-primary",
  link: "#",
  text: "connect with a planner",
  position: "unset",
  buttonstate: "default",
  texttransform: "default"
}
