import FormTextArea from "../form-textarea.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("TextAria", FormTextArea);
export default {
  title: "Core Components/Forms",
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
    text : {
        control: { type: "text" },
    },
    maxlength : {
      control: { type: "text" },
    }
  },
};

const TemplateFormTextArea = ({ label, ...args }) => FormTextArea({ ...args });
export const TextArea = TemplateFormTextArea.bind();
TextArea.args = {
    formlabel: "Comments",
    placeholder: "Please type your comment here.",
    variant: "efe-default",
    maxlength: "500"
}

