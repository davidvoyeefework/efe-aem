import ImageCaption from "../image-caption.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("ImageCaption", ImageCaption);
export default {
  title: "Core Components/Image",
  argTypes: {
    variant: {
      options: [ "image-caption"],
      control: { type: "radio" },
    },
    caption: {
        control: "text",
    },
    borderradius: {
        options: [ "round", "square"],
        control: { type: "radio" },
      },
  },
};

const TemplateImageCaption = ({ label, ...args }) => ImageCaption({ ...args });
export const CaptionImage = TemplateImageCaption.bind();
CaptionImage.args = {
  variant: "image-caption",
  caption: "Caption ut vulputate nibh aliquam massa dui id. Amet lacus morbi in ut.",
  borderradius: "round"
}


