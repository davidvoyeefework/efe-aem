import SVGImage from "../svg-icon.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("SVGImage", SVGImage);
export default {
  title: "Core Components/Image",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    variant: {
      options: [ "arrow"],
      control: { type: "radio" }
    },
    direction: {
        options: [ "up", "down", "left", "right"],
        control: { type: "radio" }
    },
    id: {
        control: { type: "text" },
    },
    source: {
        control: 'text'
    }
  },
};

const TemplateSVGImage = ({ label, ...args }) => SVGImage({ ...args });
export const SVGIcons = TemplateSVGImage.bind();
SVGIcons.args = {
    variant: "arrow",
    direction: "right",
    id:"test",
    source: "data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%3E%3C/svg%3E"
}

