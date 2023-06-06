import Handlebars from "handlebars/runtime.js";
import CustomVideo from "../custom-video.hbs";

Handlebars.registerPartial("CustomVideo", CustomVideo);

export default {
    title: "Components/CustomVideo",
    argTypes: {
      "min-height": {
        control: "boolean",
      }
    },
};

const TemplateCustomVideo = (args) => CustomVideo({ ...args });
TemplateCustomVideo.bind({
  "min-height": true,
});

export { TemplateCustomVideo as VideoEmbed } ;
