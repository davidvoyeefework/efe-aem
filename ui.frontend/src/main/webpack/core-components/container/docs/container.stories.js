import Handlebars from "handlebars/runtime.js";
import Container from '../bg-color.hbs';
Handlebars.registerPartial("Container", Container);
export default {
  title: "Core Components/Container",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["bg-efe-gray-light", "bg-efe-gray-mid", "unset", "white"],
      control: { type: "radio" },
    },
    maxWidth: {
      options: ["1920", "1400", "none"],
      control: { type: "radio" },
    },
    id: {
      control: { type: "text" },
    },
    padding: {
      options: ["unset", "26"],
      control: { type: "radio" },
    },
    borderRadius:{
    options: ['top-left', 'top-right','bottom-left', 'bottom-right'],
    control: { type: "select" },
  },
  },
};

const TemplateContainer = ({ label, ...args }) => Container({ ...args });
export const BackgroundColor = TemplateContainer.bind();
BackgroundColor.args = {
  variant: "bg-efe-gray-light",
  maxWidth: "unset",
  padding: "unset"
}
