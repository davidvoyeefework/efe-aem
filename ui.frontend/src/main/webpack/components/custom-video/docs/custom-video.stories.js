import Handlebars from "handlebars/runtime.js";
import CustomVideo from "../custom-video.hbs";

Handlebars.registerPartial("CustomVideo", CustomVideo);

export default {
    title: "Components/CustomVideo",
    argTypes: {},
};

const TemplateCustomVideo = (args) => CustomVideo({ ...args });
TemplateCustomVideo.bind({});

export { TemplateCustomVideo as VideoEmbed } ;
