import Brightcove from "../fe-brightcove.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("Brightcove", Brightcove);
export default {
    title: "FE Components/Brightcove",
    argTypes: {
        variation: {
            options: ["large-height"],
            control: { type: "radio" },
        },
    },
};

const TemplateBrightcove = ({ label, ...args }) => Brightcove({...args});
export const BrightcoveComp = TemplateBrightcove.bind();