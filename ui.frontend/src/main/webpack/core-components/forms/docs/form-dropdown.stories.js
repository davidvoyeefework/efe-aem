import FormDropDown from "../form-dropdown.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("FormDropDown", FormDropDown);
export default {
  title: "Core Components/Forms",
  argTypes: {
    dropdownlabel: {
      control: { type: "text" },
    },
    defaulttext: {
        control: { type: "text" },
      },
    variant: {
        options: ["default"],
        control: { type: "radio" },
    }
  }
};

const TemplateFormDropDown = ({ label, ...args }) => FormDropDown({ ...args });
export const DropDown = TemplateFormDropDown.bind();
DropDown.args = {
    dropdownlabel: "Assets",
    defaulttext: "Select your asset level",
    variant: "default"
}

