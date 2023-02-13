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
      options: ["bg-efe-gray-light", "bg-efe-gray-mid", "unset"],
      control: { type: "radio" },
    },
    maxWidth: {
      options: ["1920", "1400", "unset"],
      control: { type: "radio" },
    },
  },
};

const TemplateContainer = ({ label, ...args }) => Container({ ...args });
export const BackgroundColor = TemplateContainer.bind();
BackgroundColor.args = {
  variant: "bg-efe-gray-light",
  maxWidth: "unset"
}
