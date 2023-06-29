import Handlebars from "handlebars/runtime.js";
import listRTE from "../list-rte.hbs";
Handlebars.registerPartial("ListRTE", listRTE);
export default {
  title: "Core Components/List",
  argTypes: {
    text: {
      control: { type: "text" },
    },
    listtype: {
      options: ["ul", "ol"],
      control: { type: "radio" },
    },
    variant: {
      options: ["default", "large"],
      control: { type: "radio" },
    },

  },
};

const TemplateList = ({ label, ...args }) => listRTE({ ...args });
export const ListRTE = TemplateList.bind();
ListRTE.args = {
  listtype: "unordered",
  variant: "default",
}
