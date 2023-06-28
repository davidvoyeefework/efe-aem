import Forms from "../forms.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("Forms", Forms);
export default {
  title: "Core Components/Forms page",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
  },
};

const TemplateForms = ({ label, ...args }) => Forms({ ...args });
export const FormsPage = TemplateForms.bind();


