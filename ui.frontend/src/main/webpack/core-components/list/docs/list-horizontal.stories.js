import HandleBars from "handlebars/runtime";
import List from "../list.hbs";
HandleBars.registerPartial("List", List);

export default {
  title: "Core Components/List",
  argTypes: {
    variant: {
      options: ["primary"],
      control: { type: "radio" },
    },
    content: {
      control: { type: "text" },
    },
  },
};

const HorizontalList = ({...args}) => List({...args});
export const horizontal = HorizontalList.bind({});
horizontal.args = {
  variation: "horizontal",
};
