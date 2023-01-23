import Handlebars from "handlebars/runtime.js";
import ButtonLinked from "../button-linked.hbs";
import Button from "../button.hbs";
Handlebars.registerPartial("Button", Button);
Handlebars.registerPartial("ButtonLinked", ButtonLinked);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Core Components/Button",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["primary", "secondary"],
      control: { type: "radio" },
    },
  },
};

const TemplateLinked = ({ label, ...args }) => ButtonLinked;
export const Linked = TemplateLinked.bind();

const TemplateIcon = ({ label, ...args }) => Button;
export const Icon = TemplateIcon.bind();

// const Template = ({ label, ...args }) => ButtonStandard;
// export const Standard = Template.bind();
