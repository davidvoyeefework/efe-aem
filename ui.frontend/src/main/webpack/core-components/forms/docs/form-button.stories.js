import FormButton from "../form-button.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("FormButton", FormButton);
export default {
  title: "Core Components/Forms",
  argTypes: {
    text: {
      control: { type: "text" },
    },

    state: {
        options: ["default", "hover", "selected"],
        control: { type: "radio" },
    },
    variant: {
        options: ["answer", "chip"],
        control: { type: "radio" },
    }
  }
};

const TemplateFormButton = ({ label, ...args }) => FormButton({ ...args });
export const Button = TemplateFormButton.bind();
Button.args = {
    text: "Leave a legacy for my loved ones.",
    state: "default",
    variant: "answer"
}

