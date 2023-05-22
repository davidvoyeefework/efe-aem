import Handlebars from "handlebars/runtime.js";
import PlannerBio from "../planner-bio.hbs";
Handlebars.registerPartial("PlannerBio", PlannerBio);

export default {
  title: "Components/PlannerBio",
  argTypes: {},
};

const TemplatePlannerBio = (args) => PlannerBio({...args});
export const plannerBio = TemplatePlannerBio.bind({});
