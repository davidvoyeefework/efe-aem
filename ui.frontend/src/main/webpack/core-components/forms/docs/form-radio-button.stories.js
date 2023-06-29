import FormRadioButton from "../form-radio-button.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("RadioButton", FormRadioButton);
export default {
  title: "Core Components/Forms",
  argTypes: {
    radiolabel: {
      control: { type: "text" },
    },
    value: {
        control: { type: "text" },
    },
    name: {
        control: { type: "text" },
    },
    state: {
        options: ["default", "hover", "selected", "selected-hover", "disabled", "selected-disabled"],
        control: { type: "radio" },
    },
    variant: {
        options: ["radio", "checkbox"],
        control: { type: "radio" },
    }

  }
};

const TemplateFormRadioButton = ({ label, ...args }) => FormRadioButton({ ...args });
export const RadioButton = TemplateFormRadioButton.bind();
RadioButton.args = {
    radiolabel: "Satisfied",
    value: "Satisfied",
    name: "Satisfaction",
    state: "default",
    variant: "radio"
}

