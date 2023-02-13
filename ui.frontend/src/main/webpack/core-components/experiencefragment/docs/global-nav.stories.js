import Handlebars from "handlebars/runtime.js";
import GlobalNav from '../global-nav.hbs';
Handlebars.registerPartial("GlobalNav", GlobalNav);
export default {
  title: "Core Components/Experience Fragments",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {

  },
};

const TemplateContainer = ({ label, ...args }) => GlobalNav({ ...args });
export const GlobalNavigation = TemplateContainer.bind();
GlobalNavigation.args = {
}
