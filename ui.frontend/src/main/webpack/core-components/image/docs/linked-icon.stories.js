import LinkedImage from "../linked-image.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("LinkedImage", LinkedImage);
export default {
  title: "Core Components/Image",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    variant: {
      options: [ "facebook", "twitter", "linkedin", "search", "close", "hamburger"],
      control: { type: "radio" },
    },
    link: {
        control: { type: "text" },
      },
    altText : {
      control: 'text'
    },
    titleText : {
      control: 'text'
    },
    size: {
      control: {type: "select"},
      options: ["small"]
    },
    source: {
        control: 'text'
    },
    id: {
      control: 'text'
    }
  },
};

const TemplateLinkedImage = ({ label, ...args }) => LinkedImage({ ...args });
export const LinkedIcons = TemplateLinkedImage.bind();
LinkedIcons.args = {
variant: "facebook",
  link: "#", 
  size: "small",
  source: "data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%3E%3C/svg%3E"
}

// const TemplateSvgGif = ({ label, ...args }) => SvgGifImage();
// export const SvgGif = TemplateSvgGif.bind();
