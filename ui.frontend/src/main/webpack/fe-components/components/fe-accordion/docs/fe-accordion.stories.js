import Handlebars from "handlebars/runtime.js";
import FeAccordion from "../fe-accordion.hbs";

Handlebars.registerPartial("FeAccordion", FeAccordion);
export default {
    title: "FE Components/FeAccordion",
};
const TemplateFeAccordion = ({ label, ...args }) => FeAccordion({...args});
export const FeAccordionComponent = TemplateFeAccordion.bind();
