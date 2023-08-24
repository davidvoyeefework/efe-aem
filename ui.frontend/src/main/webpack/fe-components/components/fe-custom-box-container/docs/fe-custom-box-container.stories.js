import FeCustomBox from "../fe-custom-box-container.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("FeCustomBox", FeCustomBox);
export default {
    title: "FE Components/FeCustomBox",
};

const TemplateFeCustomBox = ({ label, ...args }) => FeCustomBox({...args});
export const CustomBoxComponent = TemplateFeCustomBox.bind();

