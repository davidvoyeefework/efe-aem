import FormText from "../form-text.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("TextField", FormText);
export default {
  title: "Core Components/Forms",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    formlabel: {
        control: { type: "text" },
    },
    placeholder: {
      control: { type: "text" },
    },
    variant: {
        options: ["efe-default"],
        control: { type: "radio" },
      },
    state: {
        options: ["default", "error", "disabled"],
        control: { type: "radio" },
    }
  },
};

const TemplateFormText = ({ label, ...args }) => FormText({ ...args });
export const InputField = TemplateFormText.bind();
InputField.args = {
    formlabel: "First Name",
    placeholder: "First Name",
    variant: "efe-default",
    state: "default"
}

