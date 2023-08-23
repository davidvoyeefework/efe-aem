import Handlebars from "handlebars/runtime.js";
import ListLayout from "../list-layout-three-column.hbs";

Handlebars.registerPartial("ListLayout", ListLayout);
export default {
    title: "FE Components/ListLayout",
};
const TemplateListLayout = ({ label, ...args }) => ListLayout({...args});
export const listLayout = TemplateListLayout.bind();
