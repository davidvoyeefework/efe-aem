import Handlebars from "handlebars/runtime.js";
import Tabs from "../tabs.hbs";
Handlebars.registerPartial("Tabs", Tabs);

export default {
  title: "Core Components/Tabs",
  argTypes: {
    variation: {
      control: 'radio',
      options: ['horizontal', 'vertical'],
    },
    align: {
      control: 'radio',
      options: ['center', 'left', 'right'],
    },
    background: {
      control: 'radio',
      options: ['white', 'light-gray', 'none'],
    }
  },
};

const TemplateTabs = (args) => Tabs({...args});
export const CustomTabs = TemplateTabs.bind({});
CustomTabs.args = {
  variation: "horizontal",
  align: "left",
  background: "none",
}
