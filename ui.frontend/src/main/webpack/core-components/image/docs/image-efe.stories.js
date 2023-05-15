import ImageRTE from "../image-rte.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("ImageRTE", ImageRTE);
export default {
  title: "Core Components/Image",
  argTypes: {
    variant: {
      options: [ "image-rte", "profile-thumb", "planner-hero"],
      control: { type: "radio" },
    },
    borderradius: {
        options: [ "round", "square"],
        control: { type: "radio" },
      },
  },
};

const TemplateRTEImage = ({ label, ...args }) => ImageRTE({ ...args });
export const EFEImage = TemplateRTEImage.bind();
EFEImage.args = {
  variant: "image-rte",
  borderradius: "round"
}


